package com.calclang.runtime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;

import Jama.Matrix;


public class MatrixObject extends CalcObject {

	private final Matrix matrix;
	
	public MatrixObject() {
		super(CType.MATRIX);
		matrix = new Matrix(0, 0);
	}
	
	public MatrixObject(IntObject i) {
		super(CType.MATRIX);
		matrix = new Matrix(1, 1, (double) i.getValue());
	}
	
	public MatrixObject(MatrixObject[] rows) {
		super(CType.MATRIX);
		if (rows.length == 0) {
			matrix = new Matrix(0, 0);
		} else if (rows.length == 1) {
			matrix = rows[0].matrix.copy();
		} else {
			// assert that the rows all are the same width
			int width = rows[0].matrix.getColumnDimension();
			int height = rows[0].matrix.getRowDimension();
			for (int i = 1; i < rows.length; i++) {
				if (rows[i].matrix.getColumnDimension() != width) 
					throw new MatrixSizeMismatchException(
							"Excepted " + width + " columns, got " + 
							rows[i].matrix.getColumnDimension() + " columns instead");
				height += rows[i].matrix.getRowDimension();
			}
			//System.out.println("width = " + width);
			//System.out.println("height = " + height);
			matrix = new Matrix(height, width);
			int curHeight = 0;
			for (int i = 0; i < rows.length; i++) {
				//System.out.println("curHeight: " + curHeight);
				//System.out.println("getRowDim(): " + rows[i].matrix.getRowDimension());
				matrix.setMatrix(curHeight, 
						curHeight + rows[i].matrix.getRowDimension() - 1, 
						0, width - 1, rows[i].matrix);
				curHeight += rows[i].matrix.getRowDimension();
			}
		}
	}
	
	private MatrixObject(Matrix m) {
		super(CType.MATRIX);
		this.matrix = m;
	}
	
	public static MatrixObject matrixFromCalcObject(CalcObject o) {
		if (o.getType() == CType.MATRIX) {
			return (MatrixObject) o;
		} else if (o.getType() == CType.INTEGER) {
			return new MatrixObject((IntObject) o);
		} else {
			throw new IllegalArgumentException("Invalid object: " + o.getType());
		}
	}

	Matrix getMatrix() {
		return matrix;
	}
	
	@Override
	public CalcObject add(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = (IntObject) b;
			return scalarAdd(bInt.getValue());
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			return new MatrixObject(this.matrix.plus(bMatrix.matrix));
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}

	@Override
	public CalcObject divide(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = (IntObject) b;
			return new MatrixObject(matrix.times( 1.0 / ((double)bInt.getValue()) ));
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			throw new UnsupportedOperationException("Have not implemented / for matricies yet");
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}

	@Override
	public CalcObject minus() {
		return new MatrixObject(matrix.uminus());
	}

	@Override
	public CalcObject multiply(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = (IntObject) b;
			return new MatrixObject(matrix.times(bInt.getValue()));
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			return new MatrixObject(matrix.times(bMatrix.matrix));
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}

	@Override
	public CalcObject plus() {
		return new MatrixObject(matrix.copy());
	}

	@Override
	public CalcObject power(CalcObject b) {
		throw new UnsupportedOperationException("Have not implemented ^ for matricies yet");
	}

	@Override
	public CalcObject subtract(CalcObject b) {
		if (b.getType() == CType.INTEGER) {
			IntObject bInt = (IntObject) b;
			return scalarAdd(-bInt.getValue());
		} else if (b.getType() == CType.MATRIX) {
			MatrixObject bMatrix = (MatrixObject) b;
			return new MatrixObject(matrix.minus(bMatrix.matrix));
		} else {
			throw new IllegalArgumentException("Bad object type: " + b.getType());
		}
	}
	
	public int rows() {
		return matrix.getRowDimension();
	}
	
	public int cols() {
		return matrix.getColumnDimension();
	}
	
	public MatrixObject concat(MatrixObject b) {
		if (matrix.getRowDimension() != b.matrix.getRowDimension())
			throw new MatrixSizeMismatchException("Expecting " + matrix.getRowDimension() + 
					" rows, got " + b.matrix.getRowDimension() + " instead");
		Matrix m = new Matrix(rows(), cols() + b.cols());
		m.setMatrix(0, rows() - 1, 0, cols() - 1, matrix);
		m.setMatrix(0, rows() - 1, cols(), cols() + b.cols() - 1, b.matrix);
		return new MatrixObject(m);
	}
	
	/**
	 * Returns C = A + b, where b is a scalar
	 * @param val
	 * @return
	 */
	public MatrixObject scalarAdd(int val) {
		Matrix m = matrix.copy();
		for (int i = 0; i < rows(); i++) {
			for (int j = 0; j < cols(); j++) {
				m.set(i, j, m.get(i, j) + val);
			}
		}
		return new MatrixObject(m);
	}
	
	public MatrixObject scalarMultiply(int val) {
		return new MatrixObject(matrix.times(val));
	}
	
	public String toString() {
		StringWriter writer = new StringWriter();
		PrintWriter pWriter = new PrintWriter(writer);
		matrix.print(pWriter, NumberFormat.getNumberInstance(), 5);
		return writer.toString();
	}

}

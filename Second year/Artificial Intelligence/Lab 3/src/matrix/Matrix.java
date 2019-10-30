package matrix;

public class Matrix {

	private int nrows;
	private int ncols;
	private Float[][] data;

	public Matrix(Float[][] dat) {
		this.data = dat;
		this.nrows = dat.length;
		this.ncols = dat[0].length;
	}

	public Matrix(int nrow, int ncol) {
		this.nrows = nrow;
		this.ncols = ncol;
		data = new Float[nrow][ncol];
	}

	public int getNrows() {
		return nrows;
	}

	public void setNrows(int nrows) {
		this.nrows = nrows;
	}

	public int getNcols() {
		return ncols;
	}

	public void setNcols(int ncols) {
		this.ncols = ncols;
	}

	public Float[][] getValues() {
		return data;
	}

	public void setValues(Float[][] values) {
		this.data = values;
	}

	public void setValueAt(int row, int col, Float value) {
		data[row][col] = value;
	}

	public Float getValueAt(int row, int col) {
		return data[row][col];
	}

	public boolean isSquare() {
		return nrows == ncols;
	}

	public int size() {
		if (isSquare())
			return nrows;
		return -1;
	}

	public Matrix multiplyByConstant(Float constant) {
		Matrix mat = new Matrix(nrows, ncols);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				mat.setValueAt(i, j, data[i][j] * constant);
			}
		}
		return mat;
	}

	public void printing(){
        for (int i = 0; i < getNrows(); i++) {
            for (int j = 0; j < getNcols(); j++) {
                System.out.print(getValueAt(i,j)+" ");
            }
            System.out.println();
        }
	}
}

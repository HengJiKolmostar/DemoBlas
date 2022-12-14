package com.kolmostar.blaspack;

import static org.bytedeco.openblas.global.openblas.LAPACKE_dgels;
import static org.bytedeco.openblas.global.openblas.LAPACK_ROW_MAJOR;
import static org.bytedeco.openblas.presets.openblas_nolapack.blas_get_num_threads;
import static org.bytedeco.openblas.presets.openblas_nolapack.blas_get_vendor;
import static org.bytedeco.openblas.presets.openblas_nolapack.blas_set_num_threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bytedeco.openblas.global.openblas.*;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloFromJavacpp();
    }

    private void helloFromJavacpp() {
        blas_set_num_threads(4);
        Logger.getLogger(TAG).log(Level.INFO, "vendor = " + blas_get_vendor() + ", num_threads = " + blas_get_num_threads());

        /* Locals */
        double[] A = {1, 1, 1, 2, 3, 4, 3, 5, 2, 4, 2, 5, 5, 4, 3};
        double[] b = {-10, -3, 12, 14, 14, 12, 16, 16, 18, 16};
        int info, m, n, lda, ldb, nrhs;
        int i, j;

        /* Initialization */
        m = 5;
        n = 3;
        nrhs = 2;
        lda = 3;
        ldb = 2;

        /* Print Entry Matrix */
        print_matrix_rowmajor("Entry Matrix A", m, n, A, lda);
        /* Print Right Rand Side */
        print_matrix_rowmajor("Right Hand Side b", n, nrhs, b, ldb);
//        System.out.println();

        /* Executable statements */
//        System.out.println("LAPACKE_dgels (row-major, high-level) Example Program Results");
        /* Solve least squares problem*/
        info = LAPACKE_dgels(LAPACK_ROW_MAJOR, (byte)'N', m, n, nrhs, A, lda, b, ldb);

        /* Print Solution */
        print_matrix_rowmajor("Solution", n, nrhs, b, ldb);
//        System.out.println();
//        System.exit(0);
    }

    /* Auxiliary routine: printing a matrix */
    static void print_matrix_rowmajor(String desc, int m, int n, double[] mat, int ldm) {
        int i, j;
        Logger.getLogger(TAG).log(Level.INFO, desc);

        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) Logger.getLogger(TAG).log(Level.INFO, String.valueOf(mat[i*ldm+j]));
        }
    }
}
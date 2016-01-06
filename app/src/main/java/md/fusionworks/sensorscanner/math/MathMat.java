package md.fusionworks.sensorscanner.math;


public class MathMat {
    public static double[][] pcToSoSMatrix() {
//        Create a matrix to convert point cloud positions to start of state
//        positions
        return xRotationMatrix(Math.toRadians(-90));
    }

    public static double[][] xRotationMatrix(Double ang) {
//        "" " Create a rotation matrix or a rotation around the X axis " ""
        double[][] mat = new double[4][4];
        double s = Math.sin(ang);
        double c = Math.cos(ang);
        mat[0][0] = 1;
        mat[1][1] = c;
        mat[2][1] = -s;
        mat[1][2] = s;
        mat[2][2] = c;
        mat[3][3] = 1;
        return mat;
    }

    public static double[][] sosToPCMatrix() {
//        "" "
//        Create a matrix to convert Start of Station positions to point cloud
//        positions
//        "" "
        return xRotationMatrix(Math.toRadians(90));
    }

    public static double[][] pcToDevMatrix() {
//        "" "
//        Create a matrix to convert point cloud positions to device positions
//        "" "
        return xRotationMatrix(Math.toRadians(-180));
    }

    public static double[][] devToPCMatrix() {
//            """
//    Create a matrix that converts device positions to point cloud positions
//    """
        return xRotationMatrix(Math.toRadians(180));
    }

    public static double[][] getPC2WorldMatrix(double[] t, double[] q) {
//        "" " Create a matrix that transforms point clouds to world coordinates " ""
        double[][] devToIMU = pcToDevMatrix();
        double[][] mat = quaternionToRotationMatrix(q);
        mat[3][0]=t[0];
        mat[3][1]=t[1];
        mat[3][2]=t[2];
//        return devToIMU.dot(mat);
        return dot(devToIMU, mat);
    }

    public static double[][] dot(double[][] firstarray, double[][] secondarray){
        double[][] result = new double[firstarray.length][secondarray[0].length];
        for (int i = 0; i < firstarray.length; i++) {
            for (int j = 0; j < secondarray[0].length; j++) {
                for (int k = 0; k < firstarray[0].length; k++) {
                    result[i][j] += firstarray[i][k] * secondarray[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] getTranslationMatrix(double[] t) {
//        "" " Create a translation matrix " ""
        double[][] mat = new double[4][4];
        mat[0][0]=1;
        mat[1][1]=1;
        mat[2][2]=1;
        mat[3][0]=t[0];
        mat[3][1]=t[1];
        mat[3][2]=t[2];
        mat[3][3]=1;
        return mat;
    }

    public static double[][] yRotationMatrix(double ang) {
//        "" " Create a rotation matrix for a rotation around the Y axis " ""
        double[][] mat = new double[4][4];
        double s = Math.sin(ang);
        double c = Math.cos(ang);
        mat[0][0]=c;
        mat[2][0]=s;
        mat[1][1]=1;
        mat[0][2]=-s;
        mat[2][2]=c;
        mat[3][3]=1;
        return mat;
    }

    public static double[][] zRotationMatrix(double ang) {
//        "" " Create a rotation matrix for a rotation around the Z axis " ""
        double[][] mat = new double[4][4];
        double s = Math.sin(ang);
        double c = Math.cos(ang);
        mat[0][0]=c;
        mat[1][0]=-s;
        mat[0][1]=s;
        mat[1][1]=c;
        mat[2][2]=1;
        mat[3][3]=1;
        return mat;
    }

//    //UNFINISHED
//    private double[] axisAngleToQuaternion(axisin, double angle) {
////        "" " Convert and axis and angle to a quaternion " ""
//        mag = np.linalg.norm(axisin)
//        double[] axis = np.copy(axisin) / mag
//        double s = Math.sin(angle / 2);
//        double c = Math.cos(angle / 2);
//        double[] q = new double[4];
//        q[0] = axis[0] * s;
//        q[1] = axis[1] * s;
//        q[2] = axis[2] * s;
//        q[3] = c;
//        return q;
//    }

    public static AxisA<double[], Double> quaternionToAxisAngle(double[] q) {
//        "" " Convert a quaternion to an axis & angle " ""
        double c = q[3];
        double a = Math.acos(c) * 2;
        double s = Math.sqrt(1 - c * c);
        if (Math.abs(s) < .00005) {
            s = 1;
        }
        double[] axis = new double[3];
        axis[0] = q[0] / s;
        axis[1] = q[1] / s;
        axis[2] = q[2] / s;

        AxisA<double[], Double> axisA = new AxisA<double[], Double>(axis, a);
        return axisA;
    }


    public static double[][] quaternionToRotationMatrix(double[] q) {
//        "" " Convert a quaternion to a rotation matrix " ""
        double xx = q[0] * q[0];
        double xy = q[0] * q[1];
        double xz = q[0] * q[2];
        double xw = q[0] * q[3];
        double yy = q[1] * q[1];
        double yz = q[1] * q[2];
        double yw = q[1] * q[3];
        double zz = q[2] * q[2];
        double zw = q[2] * q[3];
        double[][] mat = new double[4][4];
        mat[0][0]=1 - 2 * (yy + zz);
        mat[1][0]=2 * (xy - zw);
        mat[2][0]=2 * (xz + yw);
        mat[0][1]=2 * (xy + zw);
        mat[1][1]=1 - 2 * (xx + zz);
        mat[2][1]=2 * (yz - xw);
        mat[0][2]=2 * (xz - yw);
        mat[1][2]=2 * (yz + xw);
        mat[2][2]=1 - 2 * (xx + yy);
        mat[3][3]=1;
        return mat;
    }
}

package gregory.dan.licenceorganiser.Unit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
public class Point implements Parcelable{

    private String point;
    private boolean rectified;

    public Point(String point, boolean rectified) {
        this.point = point;
        this.rectified = rectified;
    }

    protected Point(Parcel in) {
        point = in.readString();
        rectified = in.readByte() != 0;
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public boolean isRectified() {
        return rectified;
    }

    public void setRectified(boolean rectified) {
        this.rectified = rectified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(point);
        dest.writeByte((byte) (rectified ? 1 : 0));
    }
}

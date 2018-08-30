package gregory.dan.licenceorganiser.Unit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
public class Licence implements Parcelable{

    private String licenceType, licenceSerial;
    private ArrayList<Ammunition> ammunition;
    private long issueDate;

    public Licence(String licenceType, String licenceSerial, ArrayList<Ammunition> ammunition, long issueDate) {
        this.licenceType = licenceType;
        this.licenceSerial = licenceSerial;
        this.ammunition = ammunition;
        this.issueDate = issueDate;
    }

    protected Licence(Parcel in) {
        licenceType = in.readString();
        licenceSerial = in.readString();
        ammunition = in.createTypedArrayList(Ammunition.CREATOR);
        issueDate = in.readLong();
    }

    public static final Creator<Licence> CREATOR = new Creator<Licence>() {
        @Override
        public Licence createFromParcel(Parcel in) {
            return new Licence(in);
        }

        @Override
        public Licence[] newArray(int size) {
            return new Licence[size];
        }
    };

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getLicenceSerial() {
        return licenceSerial;
    }

    public void setLicenceSerial(String licenceSerial) {
        this.licenceSerial = licenceSerial;
    }

    public ArrayList<Ammunition> getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(ArrayList<Ammunition> ammunition) {
        this.ammunition = ammunition;
    }

    public long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(long issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(licenceType);
        dest.writeString(licenceSerial);
        dest.writeTypedList(ammunition);
        dest.writeLong(issueDate);
    }
}

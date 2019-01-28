package gregory.dan.licenceorganiser.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Inspection;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.Unit.database.AppRepository.AMMUNITION_REF_TEXT;
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.INSPECTIONS_REF_TEXT;
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.LICENCE_REF_TEXT;
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.POINTS_REF_TEXT;
import static gregory.dan.licenceorganiser.Unit.database.AppRepository.UNIT_REF_TEXT;

/**
 * Created by Daniel Gregory on 05/09/2018.
 */
public class FireBaseDatabaseUtilities {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUnitRef;
    private DatabaseReference mLicenceRef;
    private DatabaseReference mInspectionRef;
    private DatabaseReference mPointsRef;
    private DatabaseReference mAmmunitionRef;
    private MyViewModel myViewModel;


    public FireBaseDatabaseUtilities(MyViewModel viewModel){
        myViewModel = viewModel;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUnitRef = mFirebaseDatabase.getReference(UNIT_REF_TEXT);
        mLicenceRef = mFirebaseDatabase.getReference(LICENCE_REF_TEXT);
        mInspectionRef = mFirebaseDatabase.getReference(INSPECTIONS_REF_TEXT);
        mPointsRef = mFirebaseDatabase.getReference(POINTS_REF_TEXT);
        mAmmunitionRef = mFirebaseDatabase.getReference(AMMUNITION_REF_TEXT);
    }

    public FireBaseDatabaseUtilities(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUnitRef = mFirebaseDatabase.getReference(UNIT_REF_TEXT);
        mLicenceRef = mFirebaseDatabase.getReference(LICENCE_REF_TEXT);
        mInspectionRef = mFirebaseDatabase.getReference(INSPECTIONS_REF_TEXT);
        mPointsRef = mFirebaseDatabase.getReference(POINTS_REF_TEXT);
        mAmmunitionRef = mFirebaseDatabase.getReference(AMMUNITION_REF_TEXT);
    }


    /*functions to set up the database*/
    public void setupDatabase() {
        // get input the units
        mUnitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String unitTitle = (String) data.child("unitTitle").getValue();
                        String unitAddress = (String) data.child("unitAddress").getValue();
                        String unitContactNumber = (String) data.child("unitContactNumber").getValue();
                        String unitCO = (String) data.child("unitCO").getValue();
                        myViewModel.insertUnit(new Unit(unitTitle,
                                unitAddress,
                                unitContactNumber,
                                unitCO));
                    }
                    setupLicences();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupLicences() {
        // get input the licences
        mLicenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String licenceSerial = (String) data.child("licenceSerial").getValue();
                        String unitTitle = (String) data.child("unitTitle").getValue();
                        String licenceType = (String) data.child("licenceType").getValue();
                        long licenceIssueDate = (long) data.child("licenceIssueDate").getValue();
                        long licenceRenewalDate = (long) data.child("licenceRenewalDate").getValue();
                        myViewModel.insertLicence(new Licence(licenceSerial,
                                unitTitle,
                                licenceType,
                                licenceIssueDate,
                                licenceRenewalDate));
                    }
                    setupInspection();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupInspection() {
        // get input the inspections
        mInspectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        long id = (long) data.child("_id").getValue();
                        String unit = (String) data.child("unit").getValue();
                        long hasPoints = (long) data.child("hasPoints").getValue();
                        long inspectionDate = (long) data.child("inspectionDate").getValue();
                        long reminderDate = (long) data.child("reminderDate").getValue();
                        long nextInspectionDue = (long) data.child("nextInspectionDue").getValue();
                        String inspectedBy = (String) data.child("inspectedBy").getValue();
                        myViewModel.insertInspection(new Inspection(unit,
                                hasPoints,
                                inspectionDate,
                                reminderDate,
                                nextInspectionDue,
                                inspectedBy,
                                id));
                    }
                    setupPoints();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupPoints() {
        // get input the points
        mPointsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        long id = (long) data.child("id").getValue();
                        long inspectionId = (long) data.child("inspectionId").getValue();
                        String point = (String) data.child("point").getValue();
                        long complete = (long) data.child("complete").getValue();
                        myViewModel.insertPoint(new OutstandingPoints(inspectionId,
                                point,
                                complete,
                                id));
                    }
                    setupAmmunition();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupAmmunition() {
        // get input the ammunition
        mAmmunitionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String licenceSerial = (String) data.child("licenceSerial").getValue();
                        String adac = (String) data.child("adac").getValue();
                        String description = (String) data.child("description").getValue();
                        String HCC = (String) data.child("HCC").getValue();
                        long quantity = (long) data.child("quantity").getValue();
                        long id = (long) data.child("id").getValue();
                        myViewModel.insertAmmunition(new Ammunition(licenceSerial,
                                adac,
                                description,
                                HCC,
                                quantity,
                                id));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*delete functions*/

    public void deleteUnit(final String unitName){
        mUnitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("unitTitle").getValue().equals(unitName)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mUnitRef.child(path).removeValue();
                            deleteAllUnitLicences(unitName);
                            deleteAllUnitInspections(unitName);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteAllUnitLicences(final String unitName){
        mLicenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        if(data.child("unitTitle").getValue().equals(unitName)){
                            String key = data.getKey();
                            String path = "/" + key;
                            mLicenceRef.child(path).removeValue();
                            String serial = (String) data.child("licenceSerial").getValue();
                            deleteAllLicenceAmmo(serial);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteLicence(final String licenceSerial){
        mLicenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        if(data.child("licenceSerial").getValue().equals(licenceSerial)){
                            String key = data.getKey();
                            String path = "/" + key;
                            mLicenceRef.child(path).removeValue();
                            deleteAllLicenceAmmo(licenceSerial);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteAllLicenceAmmo(final String licenceSerial){
        mAmmunitionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        if(data.child("licenceSerial").getValue().equals(licenceSerial)){
                            String key = data.getKey();
                            String path = "/" + key;
                            mAmmunitionRef.child(path).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteAmmo(final long ammoId){
        mAmmunitionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        if(data.child("id").getValue().equals(ammoId)){
                            String key = data.getKey();
                            String path = "/" + key;
                            mAmmunitionRef.child(path).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteAllUnitInspections(final String unitName){
        mInspectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("unit").getValue().equals(unitName)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mInspectionRef.child(path).removeValue();
                            long inspId = (long) data.child("_id").getValue();
                            deleteAllInspectionPoints(inspId);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteInspection(final long id){
        mInspectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("_id").getValue().equals(id)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mInspectionRef.child(path).removeValue();
                            deleteAllInspectionPoints(id);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteAllInspectionPoints(final long inspId){
        mPointsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("inspectionId").getValue().equals(inspId)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mPointsRef.child(path).removeValue();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deletePoint(final long id){
        mPointsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("id").getValue().equals(id)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mPointsRef.child(path).removeValue();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inseertOrUpdateFirebaseUnit(final Unit unit) {
        mUnitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                if (dataSnapshot.getValue() == null) {
                    mUnitRef.push().setValue(unit);
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("unitTitle").getValue().equals(unit.unitTitle)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mUnitRef.child(path).setValue(unit);
                            return;
                        } else if(i == dataSnapshot.getChildrenCount()){
                            mUnitRef.push().setValue(unit);
                        }

                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inseertOrUpdateFirebaseLicence(final Licence licence) {
        mLicenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                if (dataSnapshot.getValue() == null) {
                    mLicenceRef.push().setValue(licence);
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("unitTitle").getValue().equals(licence.unitTitle) && data.child("licenceSerial").getValue().equals(licence.licenceSerial)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mLicenceRef.child(path).setValue(licence);
                            return;
                        } else if(i == dataSnapshot.getChildrenCount()){
                            mLicenceRef.push().setValue(licence);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inseertOrUpdateFirebaseAmmunition(final Ammunition ammunition) {
        mAmmunitionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                if (dataSnapshot.getValue() == null) {
                    mAmmunitionRef.push().setValue(ammunition);
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("adac").getValue().equals(ammunition.adac) && data.child("licenceSerial").getValue().equals(ammunition.licenceSerial)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mAmmunitionRef.child(path).setValue(ammunition);
                            return;
                        } else if(i == dataSnapshot.getChildrenCount()){
                            mAmmunitionRef.push().setValue(ammunition);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inseertOrUpdateFirebaseInspection(final Inspection inspection) {
        mInspectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                if (dataSnapshot.getValue() == null) {
                    mInspectionRef.push().setValue(inspection);
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("unit").getValue().equals(inspection.unit) && data.child("_id").getValue().equals(inspection._id)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mInspectionRef.child(path).setValue(inspection);
                            return;
                        } else if(i == dataSnapshot.getChildrenCount()){
                            mInspectionRef.push().setValue(inspection);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inseertOrUpdateFirebasePoint(final OutstandingPoints point) {
        mPointsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                if (dataSnapshot.getValue() == null) {
                    mPointsRef.push().setValue(point);
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("id").getValue().equals(point.id)) {
                            String key = data.getKey();
                            String path = "/" + key;
                            mPointsRef.child(path).setValue(point);
                            break;
                        } else if(i == dataSnapshot.getChildrenCount()){
                            mPointsRef.push().setValue(point);
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

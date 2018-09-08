package gregory.dan.licenceorganiser.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.Unit;

import static gregory.dan.licenceorganiser.Unit.database.AppRepository.UNIT_REF_TEXT;

/**
 * Created by Daniel Gregory on 08/09/2018.
 */
public class UnitWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return new UnitWidgetViewFactory(getApplicationContext(), intent);
    }

    class UnitWidgetViewFactory implements RemoteViewsFactory {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference mRef;

        private Context context;
        private List<Unit> mUnits;
        private int mWidgetId;

        UnitWidgetViewFactory(Context context, Intent intent){
            this.context = context;
            this.mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mRef = firebaseDatabase.getReference(UNIT_REF_TEXT);
            mUnits = getmUnits();
        }

        @Override
        public void onDataSetChanged() {
            mUnits = getmUnits();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(mUnits == null){
                return 0;
            }
            return mUnits.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_item);
            String unitName = mUnits.get(position).unitTitle;
            String unitAddress = mUnits.get(position).unitAddress;
            String unitNum = mUnits.get(position).unitContactNumber;
            remoteViews.setTextViewText(R.id.unit_widget_unit_title, unitName);
            remoteViews.setTextViewText(R.id.unit_widget_address, unitAddress);
            remoteViews.setTextViewText(R.id.unit_widget_tel_no, unitNum);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private List<Unit> getmUnits(){
            final List<Unit> units = new ArrayList<>();
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                    } else {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String unitTitle = (String) data.child("unitTitle").getValue();
                            String unitAddress = (String) data.child("unitAddress").getValue();
                            String unitContactNumber = (String) data.child("unitContactNumber").getValue();
                            String unitCO = (String) data.child("unitCO").getValue();
                            units.add(new Unit(unitTitle, unitAddress, unitContactNumber, unitCO));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Database: ", databaseError.getMessage());
                }
            });
            return units;
        }
    }
}

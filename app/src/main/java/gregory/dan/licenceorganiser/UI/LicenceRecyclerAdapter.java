package gregory.dan.licenceorganiser.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.Licence;

/**
 * Created by Daniel Gregory on 01/09/2018.
 */
public class LicenceRecyclerAdapter extends RecyclerView.Adapter<LicenceRecyclerAdapter.LicenceViewHolder> {

    private List<Licence> licences;
    private LicenceRecyclerAdapter.ListItemClickListener mListItemClickListener;
    private Context context;

    @NonNull
    @Override
    public LicenceRecyclerAdapter.LicenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutId = R.layout.licence_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new LicenceRecyclerAdapter.LicenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LicenceRecyclerAdapter.LicenceViewHolder holder, int position) {
        if (licences == null) {
            return;
        }
        String mLicenceSerial = licences.get(position).licenceSerial;
        long mIssueDateRaw = licences.get(position).licenceIssueDate;
        Date missueDate = new Date(mIssueDateRaw);
        String issueDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(missueDate);
        long mExpiryDateRaw = licences.get(position).licenceRenewalDate;
        Date mExpiryDate = new Date(mExpiryDateRaw);
        String expiryDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(mExpiryDate);
        String mLicenceType = licences.get(position).licenceType;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mExpiryDateRaw);
        calendar.add(Calendar.MONTH, -2);
        long startRenewingLicence = calendar.getTimeInMillis();
        if(startRenewingLicence - System.currentTimeMillis() < 0){
            holder.mConstraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        holder.mExpiryDateTextView.setText(expiryDate);
        holder.mIssueDateTextView.setText(issueDate);
        holder.mLicenceTypeTextView.setText(mLicenceType);
        holder.mSerialTextView.setText(mLicenceSerial);
    }

    @Override
    public int getItemCount() {
        if (licences != null) {
            return licences.size();
        } else {
            return 0;
        }
    }

    public interface ListItemClickListener {
        void onClick(int item);
    }

    public LicenceRecyclerAdapter(LicenceRecyclerAdapter.ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class LicenceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.licence_item_expiry_date_text_view)
        TextView mExpiryDateTextView;
        @BindView(R.id.licence_item_issue_date_text_view)
        TextView mIssueDateTextView;
        @BindView(R.id.licence_item_serial_text_view)
        TextView mSerialTextView;
        @BindView(R.id.licence_item_type_text_view)
        TextView mLicenceTypeTextView;
        @BindView(R.id.licence_list_item_constraint_layout)
        ConstraintLayout mConstraintLayout;

        public LicenceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onClick(getAdapterPosition());
        }
    }

    public void setLicences(List<Licence> licences) {
        this.licences = licences;
        notifyDataSetChanged();
    }


}

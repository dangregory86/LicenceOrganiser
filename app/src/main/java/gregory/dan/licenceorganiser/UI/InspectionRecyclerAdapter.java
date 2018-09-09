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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.Inspection;

/**
 * Created by Daniel Gregory on 02/09/2018.
 */
public class InspectionRecyclerAdapter extends RecyclerView.Adapter<InspectionRecyclerAdapter.InspectionViewHolder> {

    private List<Inspection> inspections;
    private InspectionRecyclerAdapter.ListItemClickListener mListItemClickListener;
    private Context context;

    @NonNull
    @Override
    public InspectionRecyclerAdapter.InspectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutId = R.layout.inspection_item_layout;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new InspectionRecyclerAdapter.InspectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionRecyclerAdapter.InspectionViewHolder holder, int position) {
        if (inspections == null) {
            return;
        }
        long mInspectedDate = inspections.get(position).inspectionDate;
        Date mDate = new Date(mInspectedDate);
        String inspectedDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(mDate);
        if (inspections.get(position).hasPoints != 0) {
            holder.mConstraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        holder.mInspectionDateTextView.setText(inspectedDate);
    }

    @Override
    public int getItemCount() {
        if (inspections != null) {
            return inspections.size();
        } else {
            return 0;
        }
    }

    public interface ListItemClickListener {
        void onClickInspection(int item);
    }

    public InspectionRecyclerAdapter(InspectionRecyclerAdapter.ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class InspectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.inspection_item_date_text_view)
        TextView mInspectionDateTextView;
        @BindView(R.id.inspection_item_constraint_layout)
        ConstraintLayout mConstraintLayout;

        InspectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onClickInspection(getAdapterPosition());
        }
    }

    public void setInspections(List<Inspection> inspections) {
        this.inspections = inspections;
        notifyDataSetChanged();
    }


}

package gregory.dan.licenceorganiser.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;

/**
 * Created by Daniel Gregory on 02/09/2018.
 */
public class PointRecyclerViewAdapter extends RecyclerView.Adapter<PointRecyclerViewAdapter.PointViewHolder> {

    private List<OutstandingPoints> points;
    private PointRecyclerViewAdapter.ListItemClickListener listItemClickListener;

    @NonNull
    @Override
    public PointRecyclerViewAdapter.PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.inspection_point_item_layout;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new PointRecyclerViewAdapter.PointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointRecyclerViewAdapter.PointViewHolder holder, int position) {
        if (points == null) {
            return;
        }
        String mInspectionPoint = points.get(position).point;
        if (points.get(position).complete == 1) {
            holder.mConstraintLayout.setBackgroundColor(Color.GREEN);
            holder.mInspectionPoint.setTextColor(Color.BLACK);
            holder.mCompleteButton.setVisibility(View.GONE);
        }

        holder.mInspectionPoint.setText(mInspectionPoint);
    }

    @Override
    public int getItemCount() {
        if (points != null) {
            return points.size();
        } else {
            return 0;
        }
    }

    public interface ListItemClickListener{
        void completedClick(int item);
    }

    public PointRecyclerViewAdapter(PointRecyclerViewAdapter.ListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }


    public class PointViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.inspection_point_item_point_text_view)
        TextView mInspectionPoint;
        @BindView(R.id.inspection_point_constraint_layout)
        ConstraintLayout mConstraintLayout;
        @BindView(R.id.inspection_point_item_point_completed_button)Button mCompleteButton;

        public PointViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mCompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listItemClickListener.completedClick(getAdapterPosition());
                    }
                }
            });
        }

    }

    public void setPoints(List<OutstandingPoints> points) {
        this.points = points;
        notifyDataSetChanged();
    }


}

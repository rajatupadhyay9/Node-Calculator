package com.example.nodecalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<HistoryEntry> list;

    public RecyclerViewAdapter(List<HistoryEntry> dataset){
        list = dataset;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getOp1().setText(String.valueOf(list.get(position).getOperand1()));
        holder.getOp2().setText(String.valueOf(list.get(position).getOperand2()));
        holder.getRes().setText(String.valueOf(list.get(position).getResult()));
        holder.getOperator().setText(list.get(position).getOperator());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView op1, op2, res, operator;

        public TextView getOp1() {
            return op1;
        }

        public TextView getOp2() {
            return op2;
        }

        public TextView getRes() {
            return res;
        }

        public TextView getOperator() {
            return operator;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            op1 = itemView.findViewById(R.id.op1);
            op2 = itemView.findViewById(R.id.op2);
            res = itemView.findViewById(R.id.res);
            operator = itemView.findViewById(R.id.operator);
        }
    }
}

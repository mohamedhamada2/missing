package com.alatheer.missing.Authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.Model.Terms.Term;
import com.alatheer.missing.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsHolder> {
    Context c;
    List<Term>terms;

    public TermsAdapter(Context c, List<Term> terms) {
        this.c = c;
        this.terms = terms;
    }

    @NonNull
    @Override
    public TermsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.terms_item,parent,false);

        return new TermsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsHolder holder, int position) {
        holder.setdata(terms.get(position));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    class  TermsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_details)
        TextView txt_details;
        public TermsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void setdata(Term term) {
            txt_title.setText(term.getTitle());
            txt_details.setText(term.getBody());
        }
    }
}

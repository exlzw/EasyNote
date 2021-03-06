package com.fwwb.easynote.Adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;

import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> noteList;
    Calendar nowCalendar=Calendar.getInstance();
    String date;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onMenuOneClick(View view,int position);
        void onMenuSecondClick(View view,int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView note;
        TextView date;
        TextView time;
        TextView location;
        ImageView locationImage;
        CheckBox checkBox;
        ImageView deleteButton;
        ImageView calendarButton;
        ConstraintLayout rootLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.title);
            note=itemView.findViewById(R.id.detail_note);
            date=itemView.findViewById(R.id.detail_date);
            time=itemView.findViewById(R.id.detail_time);
            location=itemView.findViewById(R.id.detail_location);
            locationImage=itemView.findViewById(R.id.image_location);
            checkBox=itemView.findViewById(R.id.checkbox_note);
            deleteButton=itemView.findViewById(R.id.second_menubutton);
            calendarButton=itemView.findViewById(R.id.first_menubutton);
            rootLayout=itemView.findViewById(R.id.root_layout_note);
        }
    }

    public NoteAdapter(List<Note> noteList){
        this.noteList=noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_note,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i){
        Note note=noteList.get(i);
        if(note.getTitle()==null){
            viewHolder.title.setVisibility(View.GONE);
        }else{
            viewHolder.title.setText(note.getTitle());
        }
        viewHolder.note.setText(note.getNote());
        viewHolder.note.setMaxLines(2);
        //按照距今时间设置对应日期显示方式
                if(nowCalendar.get(Calendar.YEAR)!=note.getYear()){
                    date=note.getYear()+"年"+note.getMonth()+"月"+note.getDay()+"日";
                }else{
                    if(nowCalendar.get(Calendar.MONTH)+1==note.getMonth()&&nowCalendar.get(Calendar.DATE)-1==note.getDay()){
                        date="昨天";
                    }else if(nowCalendar.get(Calendar.MONTH)+1==note.getMonth()&&nowCalendar.get(Calendar.DATE)==note.getDay()){
                        date="今天";
                    }else{
                        date=note.getMonth()+"月"+note.getDay()+"日";
            }
        }

        viewHolder.time.setText(note.getTime());
        viewHolder.date.setText(date);
        if(note.getLocation()==null){
            viewHolder.location.setVisibility(View.GONE);
            viewHolder.locationImage.setVisibility(View.GONE);
        }else{
            viewHolder.location.setText(note.getLocation());
        }

        viewHolder.checkBox.setVisibility(View.GONE);

        viewHolder.rootLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,i);
                }
            }
        });

        viewHolder.calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(onItemClickListener!=null){
                    onItemClickListener.onMenuOneClick(v,i);
                }
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(onItemClickListener!=null){
                    onItemClickListener.onMenuSecondClick(v,i);
                }
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }


}

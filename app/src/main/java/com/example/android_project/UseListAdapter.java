package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UseListAdapter extends RecyclerView.Adapter<UseListAdapter.cUseViewHolder>{

    public HashMap<Long, Boolean> CUseStateArray = new HashMap<>();


    // 인터페이스 만들어서 사용
    public interface OnItemClick{
        void  OnItemClick(int pos);
    }

    private OnItemClick OnItemClick;

    //setter
    public void setOnItemClick(UseListAdapter.OnItemClick onItemClick) {
        OnItemClick = onItemClick;
    }

    private Long[] mDataset;

    public UseListAdapter(Long[] myDataset) {
        mDataset = myDataset;
    }

    HashMap<Integer, Long> map = new HashMap<Integer, Long>();

    public UseListAdapter() {
        posArray = new ArrayList<>();
    }

    public int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private ArrayList<Long> posArray;

    public ArrayList<Long> getPosArray() {
        return posArray;
    }

    public void setPosArray(ArrayList<Long> posArray) {
        this.posArray = posArray;
    }

    private Set<Long> posSet = new HashSet<>();

    Set<Long> selectedIDs = new HashSet<>();

    private cUseAdapterCallback mCallback;

    public UseListAdapter(cUseAdapterCallback callback) {
        mCallback = callback;
    }

    public interface cUseAdapterCallback {
        void OnButtonClicked();
    }

    private EditText edtUseCount;

    private ArrayList<Contents> CUseArrayList;

    private Context context;

    private int count = 1;

    public UseListAdapter(ArrayList<Contents> ContentUseAdapter, Context context) {
        this.CUseArrayList = ContentUseAdapter;
        this.context = context;
    }

    public UseListAdapter(ArrayList<Contents> ContentUseAdapter) {
        this.CUseArrayList = ContentUseAdapter;
    }

    // 필터
    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            Toast.makeText(context, "남은 수량 만큼만 입력 가능합니다 다시 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }  // 필터끝

    class cUseViewHolder extends RecyclerView.ViewHolder {
        private TextView cUseName, cUseCount;
        private EditText edtUseCount;

        private ImageView cUse_imgView;
        private ImageButton btnPlus,btnMinus;

        private ImageButton cUseDelBtn;

        private CheckBox Content_chbox;

        public cUseViewHolder(@NonNull View itemView) {
            super(itemView);
            cUseName = (TextView) itemView.findViewById(R.id.tvcUseName);
            cUseCount = (TextView) itemView.findViewById(R.id.tvcUseCount);
            edtUseCount = (EditText) itemView.findViewById(R.id.cUse_editCount);
            btnPlus = (ImageButton) itemView.findViewById(R.id.Content_btnPlus);
            btnMinus = (ImageButton) itemView.findViewById(R.id.Content_btnMinus);
            cUse_imgView = (ImageView) itemView.findViewById(R.id.Content_imgView);
            cUseDelBtn = (ImageButton) itemView.findViewById(R.id.Content_imageButton1);
            Content_chbox = (CheckBox) itemView.findViewById(R.id.Content_chbox);

            edtUseCount.addTextChangedListener(new TextWatcher() {

                private int position;
                public void updatePosition(int position) {
                    this.position = position;
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    mDataset[position] = Long.valueOf(edtUseCount.getText().toString());
                    //     CUseArrayList.get(getAdapterPosition()).setEditTextValue((edtUseCount.getText().toString()));
                    CUseArrayList.get(getAdapterPosition()).setEditTextValue((edtUseCount.getText().toString()));
                    CUseArrayList.get(getAdapterPosition()).setPos((long) getAdapterPosition());
                    //  edtUseCount.setText(s);
                    //   notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {
//                    arrayList.get(getAdapterPosition()).setRty(s.toString());
//                    if(!rtyArray.contains(getAdapterPosition())){
//                        rtyArray.add(getAdapterPosition());}
                    //CUseArrayList.get(getAdapterPosition()).setEditTextValue((edtUseCount.getText().toString()));
                }
            });
        }
    }


    @NonNull
    @Override
    public cUseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.use_items, parent, false);
        cUseViewHolder cUseviewHolder = new cUseViewHolder(view);
        return cUseviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cUseViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Contents contents = CUseArrayList.get(position);

        holder.cUseName.setText(contents.getName());
        holder.cUseCount.setText(String.valueOf(contents.getCount()));

        holder.edtUseCount.setText(String.valueOf(1));
        //   holder.edtUseCount.setText(String.valueOf(contents.getCount()));

        holder.cUse_imgView.setImageResource(R.drawable.ic_launcher_background);

        Log.d("cot","CUseArrayList.get(position).getEditTextValue()"+CUseArrayList.get(position).getEditTextValue());

        contents.setPos((long) position);

//        for (int i = 0; i < CUseArrayList.size(); i++){
//            edtUseCount.setText(edtUseCount.getText() + " " + CUseArrayList.get(i).getName() +System.getProperty("line.separator"));
//        }

//        holder.edtUseCount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //blank;
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //  names.add(holder.edtUseCount.getText().toString());
//                String value = String.valueOf(map.get(position));  // here position is the key
//                if (value != null) {
//                    map.put(position, Long.valueOf(holder.edtUseCount.getText().toString()));
//                } else {
//                    // Key might be present...
//                    if (map.containsKey(value)) {
//                        // Okay, there's a key but the value is null
//                    } else {
//                        // Definitely no such key
//                    }
//                }
//            }
//        });

        // posSet.contains(CUseArrayList.get(position).getEditTextValue());

        //  int position = holder.getAdapterPosition();
        // pos = holder.getAdapterPosition();

        // 필터
        holder.edtUseCount.setFilters(new InputFilter[]{ new InputFilterMinMax("1", String.valueOf(contents.getCount()))});






        final int pos = position;


        //선택
        holder.Content_chbox.setChecked(selectedIDs.contains(contents.getId()));

        holder.Content_chbox.setChecked(CUseArrayList.get(position).isSelected());
        holder.Content_chbox.setTag(CUseArrayList.get(position));
        holder.Content_chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (CUseStateArray.containsKey(contents.getId())) {
//                    CUseStateArray.remove(contents.getId());
//                } else {
//                    CUseStateArray.put(contents.getId(),true);
//                    CheckBox cb = (CheckBox) v;
//                    Contents contents = (Contents) cb.getTag();
//                    contents.setSelected(cb.isChecked());
//                    CUseArrayList.get(pos).setSelected(cb.isChecked());
//
//                }

                if (selectedIDs.contains(contents.getId())) {
                    selectedIDs.remove(contents.getId());
                    contents.setPos((long) pos);
                } else {
                    selectedIDs.add(contents.getId());
                }

            }
        });












        Long dcount = contents.getCount(position);
        Log.d("cot","dcount"+contents.getCount(),null);
        if (dcount == 0){




            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Contents contents = dataSnapshot.getValue(Contents.class);

                        Log.d("ctt"," contents.getId(position):    "+ contents.getId(position),null);



                        //   if (  == contents.getId(position)  )
                        //   {

                        //    Long ids =   contents.getId(position);

                        if (CUseArrayList.size()!=0){
                            Long ids =   CUseArrayList.get(position).getId(position);

                            Log.d("ctt","ids:    "+ids,null);

                            Map<String, Object> map = new HashMap<>();
                            map.put("inUseList", 0);

                            ref.child(String.valueOf(ids)).updateChildren(map);
                        }





                        //  CUseArrayList.remove(position);

                        //      CUseArrayList.clear();






                        //    }//if

                    } //for
                    //   notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




//                ref.addValueEventListener(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            Contents contents = snapshot.getValue(Contents.class);
//
//                            Log.d("ctt"," contents.getId(position):    "+ contents.getId(position),null);
//
//
//
//                         //   if (  == contents.getId(position)  )
//                         //   {
//
//                            //    Long ids =   contents.getId(position);
//
//                      //      CUseArrayList.clear();
//
//                                 Long ids =   CUseArrayList.get(position).getId(position);
//
//                                Log.d("ctt","ids:    "+ids,null);
//
//                                Map<String, Object> map = new HashMap<>();
//                                map.put("inUseList", 0);
//
//                                ref.child(String.valueOf(ids)).updateChildren(map);
//
//
//                           //     CUseArrayList.remove(ids);
//
//                        //    }//if
//
//                        } //for
//                     //   notifyDataSetChanged();
//                    } //datachange
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    } //oncancel
//                });// addV





        } //if
















        // 플러스버튼
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                OnItemClick.OnItemClick(position);
                count = contents.getCuseCnt();
                count++;
                holder.edtUseCount.setText(count+"");
                holder.edtUseCount.setFilters(new InputFilter[]{ new InputFilterMinMax("1", String.valueOf(contents.getCount()))});

                contents.setCuseCnt(count);

            }
        });

        //마이너스 버튼
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = contents.getCuseCnt();
                count--;
                holder.edtUseCount.setText(count+"");
                holder.edtUseCount.setFilters(new InputFilter[]{ new InputFilterMinMax("1", String.valueOf(contents.getCount()))});
                contents.setCuseCnt(count);
            }
        });

        holder.cUseDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Contents contents = snapshot.getValue(Contents.class);


                            Long ids =   CUseArrayList.get(position).getId();

                            //  Long inUseList = Long.valueOf(0);
                            //   Long inBuyList = Long.valueOf(0);

                            //    contents.setInUseList(inUseList);

                            Map<String, Object> map = new HashMap<>();
                            map.put("inUseList", 0);

                            ref.child(String.valueOf(ids)).updateChildren(map);

                            //    ref.child(String.valueOf(ids)).setValue(contents);

                        }
//                        Intent intent = new Intent(context, UseListView.class);
//                        view.getContext().startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        switch (contents.getCategory()) {
            case "채소":
                holder.cUse_imgView.setImageResource(R.drawable.ci_vegetable);
                break;
            case "과일":
                holder.cUse_imgView.setImageResource(R.drawable.ci_fruits);
                break;
            case "육류":
                holder.cUse_imgView.setImageResource(R.drawable.ci_meat);
                break;
            case "수산물":
                holder.cUse_imgView.setImageResource(R.drawable.ci_fish);
                break;
            case "음료":
                holder.cUse_imgView.setImageResource(R.drawable.ci_drinks);
                break;
            case "유제품":
                holder.cUse_imgView.setImageResource(R.drawable.ci_dairy);
                break;
            case "인스턴트":
                holder.cUse_imgView.setImageResource(R.drawable.ci_instant);
                break;
            case "기타":
                holder.cUse_imgView.setImageResource(R.drawable.ci_etcetera);
                break;
        }

        //에딧텍스트
        //  holder.edtUseCount.setText(String.valueOf(CUseArrayList.get(position).getEditTextValue()));
        contents.setEditTextValue(String.valueOf(CUseArrayList.get(position).getEditTextValue()));
        Log.d("cot","contents.getEditTextValue()"+contents.getEditTextValue(),null);
        //  pos = position;
        //   posArray.add((long) position);
        //    Log.d("cot","pos 바인딩안:   "+pos,null);

    }

    @Override
    public int getItemCount() {
        return CUseArrayList == null? 0 : CUseArrayList.size();
    }

    public void cUseSelected() {
        for (final Long id : selectedIDs) {

            //  pos = Math.toIntExact(id);
            //  Contents contents = CUseArrayList.get(position);


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Contents");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Contents contents = dataSnapshot.getValue(Contents.class);

                        //     Log.d("cot","contents.getEditTextValue() 체크박스안"+contents.getEditTextValue(),null);
                        //       Log.d("cot","pos체크박스안:   "+pos,null);
                        //     Long tt =   mDataset[pos];
                        //   Log.d("cot"," mDataset[pos]: "+ mDataset[pos],null);
                        //    contents.setEditTextValue(String.valueOf(CUseArrayList.get(pos).getEditTextValue()));
                        //  Long ucot = Long.valueOf(contents.getEditTextValue(pos));
                        //    Log.d("cot","contents.getEditTextValue(pos): "+contents.getEditTextValue(pos),null);
                        //   Log.d("cot","contents.getEditTextValue(): "+contents.getEditTextValue(),null);
                        //      Log.d("cot","CUseArrayList.get(pos).getEditTextValue(): "+CUseArrayList.get(pos).getEditTextValue(),null);
//                        Long count;
//                        count = dataSnapshot.getChildrenCount();
//                        String is = String.valueOf(count);
                        //    Log.d("cot","CUseArrayList.get(pos).getName(): "+CUseArrayList.get(pos).getName(),null);




                        for (int i = 0; i < CUseArrayList.size(); i++){
                            Long p = CUseArrayList.get(i).getPos();
                            Log.d("cot","p값:    "+p,null);


                            Long Pid =  CUseArrayList.get(Math.toIntExact(p)).getId();
                            Log.d("cot","Pid값:    "+Pid,null);
                            Log.d("cot","id값: "+id,null);
//                            CUseArrayList.get(i).getId();

                            if (p == i){

                                if (id != null){
                                    if (id == Pid){

                                        if (Long.valueOf(CUseArrayList.get(Math.toIntExact(p)).getEditTextValue())!=null){
                                            Long ucot = Long.valueOf(CUseArrayList.get(Math.toIntExact(p)).getEditTextValue());
                                            Long cot = contents.getCount();

                                            Log.d("cot","ucot: "+ucot,null);

                                            if (id == contents.getId() ) {

                                                long Rcot = (cot) - (ucot);

                                                //     Log.d("cot","Rcot: "+Rcot,null);
                                                //  Long ucot = Long.valueOf(CUseArrayList.get(o).getEditTextValue());

                                                //    Long ucot = Long.valueOf(CUseArrayList.get(pos).getEditTextValue());

                                                Map<String, Object> map = new HashMap<>();
                                                map.put("count", Rcot);

                                                ref.child(String.valueOf(id)).updateChildren(map);
                                            }
                                        }
                                        else {
                                            Toast.makeText(context, "갯수를 입력해주세요", Toast.LENGTH_SHORT).show();
                                        }
                                    }  //if

                                }
                                else{
                                    Toast.makeText(context, "체크해주세요", Toast.LENGTH_SHORT).show();
                                }





                            }  // if


                        }  //for




//                        Long ucot = Long.valueOf(CUseArrayList.get(pos).getEditTextValue());
//
//                        Long cot = contents.getCount();
//
//                        Log.d("cot","ucot: "+ucot,null);
//
//                        if (id == contents.getId() ) {
//
//                            long Rcot = (cot) - (ucot);
//
//                            //     Log.d("cot","Rcot: "+Rcot,null);
//
//
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("count", Rcot);
//
//                            ref.child(String.valueOf(id)).updateChildren(map);
//                        }









                        //  Long ucot = Long.valueOf(CUseArrayList.get(pos).getEditTextValue());
                        //     Long ucot = map.get(pos);
                        //     Long ucot =      CUseArrayList.get(pos).getId(pos);
                        //     Log.d("cot"," contents.getId(pos);: "+ contents.getId(pos),null);
                        //   Log.d("cot","id: "+id,null);
                        //   Log.d("cot","CUseArrayList.get(pos).getId(): "+  CUseArrayList.get(pos).getId(),null);
                        // Long ids = contents.getId(pos);
//                        if (id == CUseArrayList.get(pos).getId()) {
//                        }
                        //    ref.child(String.valueOf(ids)).setValue(contents);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        selectedIDs.clear();
    }
















}

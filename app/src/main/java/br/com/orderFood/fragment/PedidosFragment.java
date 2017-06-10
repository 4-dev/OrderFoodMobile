package br.com.orderFood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.interfaces.RecyclerViewOnClickListenerHack;
import br.com.orderFood.model.entity.Pedido;

/**
 * @author Ruan Alves
 */
public class PedidosFragment extends BaseFragment implements RecyclerViewOnClickListenerHack {

    protected static final String TAG = "LOG";
    private int color;
    private RecyclerView mRecyclerView;
    private View mEmptyStateContainer;
    private List<Pedido> mList;
    private Pedido mPedidoSelecionada;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        mEmptyStateContainer = (View) view.findViewById(R.id.empty_state_container);
        setmRecyclerView(view);

        return view;

    }

    private void setmRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_entregas);
        mRecyclerView.setHasFixedSize(true);

        //Usa o click na activity e não do adapter
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

    }

    public PedidosFragment() {
    }

    @SuppressLint("ValidFragment")
    public PedidosFragment(int color) {
        this.color = color;
    }

    @Override
    public void onResume() {
        super.onResume();

        setListDados();

    }

    private void setListDados() {

        //try {

            if (mList == null) mList = new ArrayList<Pedido>();
            else mList.clear();

            if (mList != null && mList.size() > 0) {
                showYesPedidos();
            } else {
                showNotPedidos();
            }

        /*}catch (SQLException e){
            showAlert("Ops! Ocorreu um erro ao buscar entregas...");
            e.printStackTrace();
        }*/

    }

    /*public List<EntregaModel> getListEntregas() throws SQLException{

        List<EntregaModel> listEntregas = new ArrayList<>();
        if(mNfSaidaBo == null) mNfSaidaBo = new NfSaidaBO(getActivity());
        listEntregas = mNfSaidaBo.buscarEntregas();

        return listEntregas;

    }*/

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    @Override
    public void onClickListener(View view, final int positionClick) {

    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh) {
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }

                    return (true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private void showNotPedidos() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyStateContainer.setVisibility(View.VISIBLE);
    }

    private void showYesPedidos() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyStateContainer.setVisibility(View.GONE);
    }

}

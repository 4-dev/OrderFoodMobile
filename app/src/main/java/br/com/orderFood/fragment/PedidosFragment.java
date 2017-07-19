package br.com.orderFood.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.activity.DetailFullScreenActivity;
import br.com.orderFood.activity.PedidoActivity;
import br.com.orderFood.adapter.ContextMenuAdapter;
import br.com.orderFood.adapter.PedidosAdapter;
import br.com.orderFood.dto.PedidoSync;
import br.com.orderFood.interfaces.APIServiceConection;
import br.com.orderFood.interfaces.RecyclerViewOnClickListenerHack;
import br.com.orderFood.model.bo.PedidoBO;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.model.model.ContextMenuItem;
import br.com.orderFood.retrofit.PedidoSyncGson;
import br.com.orderFood.utils.UtilTCM;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ruan Alves
 */
public class PedidosFragment extends BaseFragment implements RecyclerViewOnClickListenerHack {

    private int color;
    private RecyclerView mRecyclerView;
    private View mEmptyStateContainer;
    private List<Pedido> mList;
    private PedidoBO pedidoBO;
    private PedidosAdapter mAdapter;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        mEmptyStateContainer = (View) view.findViewById(R.id.empty_state_container);

        setmRecyclerView(view);

        buttonFabEnviarPedidos(view);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        setListDados();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public Runnable changeListarDados = new Runnable() {
        @Override
        public void run() {
            setListDados();
        }
    };

    private void buttonFabEnviarPedidos(View view) {

        mFab = (FloatingActionButton) view.findViewById(R.id.fab_enviarpedidos);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissoesHabilitadas()) {
                    enviarPedidos();
                }
            }
        });

    }

    private void enviarPedidos(){

        try {

            PedidoBO pedidoBO = new PedidoBO(getActivity());
            List<Pedido> listPedidos = pedidoBO.getPedidosPendentes();
            pedidoBO = null;

            if(listPedidos != null && listPedidos.size() > 0){

                String imei = getIMEI();

                PedidoSync pedidoSync = new PedidoSync();
                pedidoSync.setListPedidos(listPedidos);
                pedidoSync.setImei(imei);
                enviarPedios(pedidoSync);

            } else {
                showAlert(getString(R.string.msg_inf_pedidospend));
            }

        }catch (SQLException e){
            showAlert(getString(R.string.msg_erro_inesperado));
        }

    }

    public String getIMEI() {

        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        if(telephonyManager != null) imei = telephonyManager.getDeviceId();
        WifiManager wifiMan = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(imei == null || imei.equals("")) {
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            imei = wifiInf.getMacAddress();
        }

        return imei;

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

    public List<Pedido> getListPedidos() throws SQLException {

        List<Pedido> listPedidos = new ArrayList<>();
        if(pedidoBO == null) pedidoBO = new PedidoBO(getActivity());
        listPedidos = pedidoBO.getPedidos();

        return listPedidos;

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    public void onEvent(Pedido pedido) {
        //setListDados();
    }

    private void showNotPedidos() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyStateContainer.setVisibility(View.VISIBLE);
    }

    private void showYesPedidos() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyStateContainer.setVisibility(View.GONE);
    }

    private void setListDados() {

        try {

            if (mList == null) mList = new ArrayList<Pedido>();
            else mList.clear();

            mList.addAll(getListPedidos());

            if(mAdapter == null)mAdapter = new PedidosAdapter(getActivity(), mList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            if (mList != null && mList.size() > 0) {
                showYesPedidos();
            } else {
                showNotPedidos();
            }

        }catch (SQLException e){
            showAlert("Ops! Ocorreu um erro ao buscar entregas...");
            e.printStackTrace();
        }

    }

    private void enviarPedios(final PedidoSync pedidoSync){

        if (UtilTCM.verifyConnection(getActivity())) {

            showProgressDialog(getString(R.string.mensagem_progress));
            String URL = "https://orderfood.cfapps.io/pedido/";
            Gson gson = new GsonBuilder().registerTypeAdapter(PedidoSync.class, new PedidoSyncGson()).create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            APIServiceConection serviceConection = retrofit.create(APIServiceConection.class);
            final Call<String> requester = serviceConection.enviarPedidos(pedidoSync);

            new Thread() {

                public void run() {

                    try {

                        Looper.prepare();

                        String retorno = requester.execute().body();
                        if (retorno != null && retorno.equalsIgnoreCase("OK")) {

                            PedidoBO pedidoBO = new PedidoBO(getActivity());
                            pedidoBO.alterarStatusPedido(pedidoSync.getListPedidos());
                            pedidoBO = null;

                            mTextToast = getString(R.string.mensagem_sucesso);
                            getActivity().runOnUiThread(changeMessageToastALERT);

                            getActivity().runOnUiThread(changeListarDados);

                        } else {
                            mTextToast = getString(R.string.mensagem_naohainformacoes);
                            getActivity().runOnUiThread(changeMessageToastALERT);
                        }

                        mProgressDialog.dismiss();
                        Thread.interrupted();

                    } catch (IOException e) {
                        mProgressDialog.dismiss();
                        mTextToast = getString(R.string.mensagem_conexao_naoestabelecida);
                        getActivity().runOnUiThread(changeMessageToastERROR);
                        Thread.interrupted();
                        e.printStackTrace();
                    } catch (Exception e) {
                        mProgressDialog.dismiss();
                        mTextToast = getString(R.string.mensagem_erro_admin);
                        getActivity().runOnUiThread(changeMessageToastERROR);
                        Thread.interrupted();
                        e.printStackTrace();
                    }

                }

            }.start();

        } else {
            showAlertRede();
        }

    }

    private boolean permissoesHabilitadas() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Log.d("NGVL", "permissoesHabilitadas::BEGIN");
            List<String> permissoes = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissoes.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (!permissoes.isEmpty()) {
                String[] array = new String[permissoes.size()];
                permissoes.toArray(array);
                ActivityCompat.requestPermissions(getActivity(), array, 1);
            }

            Log.d("NGVL", "permissoesHabilitadas::END");
            return permissoes.isEmpty();

        }

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("NGVL", "onRequestPermissionsResult::BEGIN");

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                break;
            } else {
                enviarPedidos();
            }
        }

        Log.d("NGVL", "onRequestPermissionsResult::END");

    }


    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    @Override
    public void onClickListener(View view, final int positionClick) {

        float scale = getActivity().getResources().getDisplayMetrics().density;

        List<ContextMenuItem> itens = new ArrayList<>();
        itens.add(new ContextMenuItem(R.drawable.ic_edit_cinzax_24dp, "Editar"));
        itens.add(new ContextMenuItem(R.drawable.ic_reorder_cinza_24dp , "Itens"));
        itens.add(new ContextMenuItem(R.drawable.ic_delete_cinza_24dp , "Excluir"));

        final ContextMenuAdapter adapters = new ContextMenuAdapter(getActivity(), itens);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(adapters);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setWidth((int) (240 * scale + 0.5f));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PedidosAdapter adapter = (PedidosAdapter) mRecyclerView.getAdapter();
                Pedido pedido = (Pedido) adapter.getItemAtPosition(positionClick);

                if (position == 0) {

                    listPopupWindow.dismiss();

                    if(pedido.getStatus().equalsIgnoreCase("PENDENTE")) {

                        Intent form = new Intent(getActivity(), PedidoActivity.class);
                        startActivity(form);

                        EventBus.getDefault().postSticky(pedido);
                    } else {
                        showAlert(getString(R.string.informacao_pedido_nao_pendente));
                    }

                } else if (position == 1) {

                    listPopupWindow.dismiss();
                    Intent form = new Intent(getActivity(), DetailFullScreenActivity.class);
                    startActivity(form);
                    EventBus.getDefault().postSticky(pedido);

                } else if (position == 2) {

                    if(pedido.getStatus().equalsIgnoreCase("PENDENTE")) {

                        PedidoBO pedidoBO = new PedidoBO(getActivity());
                        pedidoBO.deletarPedido(pedido);
                        pedidoBO = null;

                        listPopupWindow.dismiss();
                        showAlert(getString(R.string.mensagem_sucesso));
                        setListDados();

                    } else {
                        listPopupWindow.dismiss();
                        showAlert(getString(R.string.informacao_pedido_nao_pendente));
                    }

                }
            }
        });

        listPopupWindow.setModal(true);
        listPopupWindow.getBackground().setAlpha(0);
        listPopupWindow.show();

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

}

package br.senai.sc.projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.projeto01.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_PRODUTO = 1;
    private final int RESULT_CODE_NOVO_PRODUTO =10;
    private final int REQUEST_CODE_EDITAR_PRODUTO = 2;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.listView_produtos);
        ArrayList<Produto> produtos = new ArrayList<Produto>();
    }

    private void definirOnClickListener() {
            listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final Produto produtoClicado = adapterProdutos.getItem(position);

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon((android.R.drawable.ic_delete))
                            .setTitle("Deseja deletar?")
                            .setMessage("Deseja realmente deletar este item?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    adapterProdutos.remove(produtoClicado);
                                    adapterProdutos.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, "Produto deletado", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Não", null).show();
                    return true;
                }
            });

        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtos);
        listViewProdutos.setAdapter(adapterProdutos);

        definirOnCliListennerListView();


    }

    private void definirOnCliListennerListView () {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUTO);
            }
        });
    }


    public void onClickNovoProduto (View v) {
        Intent intent = new Intent (MainActivity.this, CadastroProdutoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_PRODUTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_PRODUTO && resultCode == RESULT_CODE_NOVO_PRODUTO) {
            Produto produto = (Produto) data.getExtras().getSerializable("novoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);
        } else if (requestCode == REQUEST_CODE_EDITAR_PRODUTO && resultCode == RESULT_CODE_PRODUTO_EDITADO) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for (int i = 0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoEditado.getId()) {
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
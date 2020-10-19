package br.senai.sc.projeto01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sc.projeto01.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_PRODUTO = 10;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;

    private boolean edicao = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");

        carregarProduto();
    }

    private void carregarProduto() {
            Intent intent = getIntent();
            if (intent != null && intent.getExtras() !=null &&
                    intent.getExtras().get("produtoEdicao") != null) {
                Produto produto =(Produto) intent.getExtras().get("produtoEdicao");
                EditText editTextNome = findViewById(R.id.editText_nome);
                EditText editTextValor = findViewById(R.id.ediText_Valor);
                editTextNome.setText(produto.getNome());
                editTextValor.setText(String.valueOf(produto.getValor()));
                edicao = true;
                id = produto.getId();
            }
        }


    public void onClickVoltar (View v) {
        finish();
    }

    public void onClickSalvar (View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextValor = findViewById(R.id.ediText_Valor);

        String nome = editTextNome.getText().toString();
        Float valor = Float.parseFloat(editTextValor.getText().toString());

        Produto produto = new Produto(id, nome, valor);
        Intent intent = new Intent();
        if (edicao) {
            intent.putExtra("produtoEditado", produto);
            setResult(RESULT_CODE_PRODUTO_EDITADO, intent);
        }else {
            intent.putExtra("novoProduto", produto);
            setResult(RESULT_CODE_NOVO_PRODUTO, intent);
        }

        finish();
    }

}
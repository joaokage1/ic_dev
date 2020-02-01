package com.example.initialphase.activities.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.initialphase.R;
import com.example.initialphase.model.Pontuacao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckListProfileActivity extends AppCompatActivity {

    Button submit;

    private static double PT = 0;

    //Socioeconômico
    RadioGroup rg_socioEconomico;
    RadioButton rb_socio_economico1,rb_socio_economico2,rb_socio_economico3;

    private void inicializaSocioEconomico() {
        rg_socioEconomico = findViewById(R.id.rg_socioEconomico);
        rb_socio_economico1 = findViewById(R.id.rb_socio_economico1);
        rb_socio_economico2 = findViewById(R.id.rb_socio_economico2);
        rb_socio_economico3 = findViewById(R.id.rb_socio_economico3);
    }

    //Academico
    RadioGroup rg_criteriosAcademicos;
    RadioButton rb_media1,rb_media2,rb_media3,rb_media4,rb_media5;

    private void inicializaCriteriosAcademicos() {
        rg_criteriosAcademicos = findViewById(R.id.rg_criteriosAcademicos);
        rb_media1 = findViewById(R.id.rb_media1);
        rb_media2 = findViewById(R.id.rb_media2);
        rb_media3 = findViewById(R.id.rb_media3);
        rb_media4 = findViewById(R.id.rb_media4);
        rb_media5 = findViewById(R.id.rb_media5);
    }

    //Pesquisa
    RadioGroup rg_projeto_pesquisa;
    RadioButton rb_ppesquisa1,rb_ppesquisa2;

    private void inicializaProjetoPesquisa() {
        rg_projeto_pesquisa = findViewById(R.id.rg_projeto_pesquisa);
        rb_ppesquisa1 = findViewById(R.id.rb_ppesquisa1);
        rb_ppesquisa2 = findViewById(R.id.rb_ppesquisa2);
    }

    //Extensão
    RadioGroup rg_projeto_extensao;
    RadioButton rb_pextensao1,rb_pextensao2;

    private void inicializaProjetoExtensao() {
        rg_projeto_extensao = findViewById(R.id.rg_projeto_extensao);
        rb_pextensao1 = findViewById(R.id.rb_pextensao1);
        rb_pextensao2 = findViewById(R.id.rb_pextensao2);
    }

    //Comissão
    RadioGroup rg_comissao;
    RadioButton rb_comissao_eventos1,rb_comissao_eventos2,rb_comissao_eventos3,rb_comissao_eventos4;

    private void inicializaComissao() {
        rg_comissao = findViewById(R.id.rg_comissao);
        rb_comissao_eventos1 = findViewById(R.id.rb_comissao_eventos1);
        rb_comissao_eventos2 = findViewById(R.id.rb_comissao_eventos2);
        rb_comissao_eventos3 = findViewById(R.id.rb_comissao_eventos3);
        rb_comissao_eventos4 = findViewById(R.id.rb_comissao_eventos4);
    }

    //Poster
    RadioGroup rg_poster;
    RadioButton rb_poster1,rb_poster2,rb_poster3,rb_poster4,rb_poster5;

    private void inicializaPoster() {
        rg_poster = findViewById(R.id.rg_poster);
        rb_poster1 = findViewById(R.id.rb_poster1);
        rb_poster2 = findViewById(R.id.rb_poster2);
        rb_poster3 = findViewById(R.id.rb_poster3);
        rb_poster4 = findViewById(R.id.rb_poster4);
        rb_poster5 = findViewById(R.id.rb_poster5);
    }

    //Poster Externo
    RadioGroup rg_poster_externo;
    RadioButton rb_poster_extern1,rb_poster_extern2,rb_poster_extern3,rb_poster_extern4,rb_poster_extern5;

    private void inicializaPosterExt() {
        rg_poster_externo = findViewById(R.id.rg_poster_externo);
        rb_poster_extern1 = findViewById(R.id.rb_poster_ext);
        rb_poster_extern2 = findViewById(R.id.rb_poster_ext2);
        rb_poster_extern3 = findViewById(R.id.rb_poster_ext3);
        rb_poster_extern4 = findViewById(R.id.rb_poster_ext4);
        rb_poster_extern5 = findViewById(R.id.rb_poster_ext5);
    }

    //Centro de idiomas
    RadioGroup rg_atividadesCI;
    RadioButton rb_atividadesCI1,rb_atividadesCI2,rb_atividadesCI3,rb_atividadesCI4;

    private void inicializaIdiomas() {
        rg_atividadesCI = findViewById(R.id.rg_atividadesCI);
        rb_atividadesCI1 = findViewById(R.id.rb_atividadesCI1);
        rb_atividadesCI2 = findViewById(R.id.rb_atividadesCI2);
        rb_atividadesCI3 = findViewById(R.id.rb_atividadesCI3);
        rb_atividadesCI4 = findViewById(R.id.rb_atividadesCI4);
    }

    //Linguas IFTM
    RadioGroup rg_linguasIFTM;
    RadioButton rb_linguasIFTM1,rb_linguasIFTM2;

    private void inicializaLinguasIFTM() {
        rg_linguasIFTM = findViewById(R.id.rg_linguasIFTM);
        rb_linguasIFTM1 = findViewById(R.id.rb_linguasIFTM1);
        rb_linguasIFTM2 = findViewById(R.id.rb_linguasIFTM2);
    }

    //Linguas Externo
    RadioGroup rg_linguasExt;
    RadioButton rb_linguasExt1,rb_linguasExt2;

    private void inicializaLinguasExt() {
        rg_linguasExt = findViewById(R.id.rg_linguasExt);
        rb_linguasExt1 = findViewById(R.id.rb_linguasExt1);
        rb_linguasExt2 = findViewById(R.id.rb_linguasExt2);
    }

    //Participações IFTM
    RadioGroup rg_participacoes_IFTM;
    RadioButton rb_participacoes_IFTM1,rb_participacoes_IFTM2,rb_participacoes_IFTM3;

    private void inicializaParticipacoes() {
        rg_participacoes_IFTM = findViewById(R.id.rg_participacoes_IFTM);
        rb_participacoes_IFTM1 = findViewById(R.id.rb_participacoes_IFTM);
        rb_participacoes_IFTM2 = findViewById(R.id.rb_participacoes_IFTM2);
        rb_participacoes_IFTM3 = findViewById(R.id.rb_participacoes_IFTM3);
    }

    //Conselho
    RadioGroup rg_conselho;
    RadioButton rb_conselho2;

    private void inicializaConselho() {
        rg_conselho = findViewById(R.id.rg_conselho);
        rb_conselho2 = findViewById(R.id.rb_conselho2);
    }

    //Banco de curriculo
    RadioGroup rg_BEEC;
    RadioButton rb_BEEC2;

    private void inicializaBEEC() {
        rg_BEEC = findViewById(R.id.rg_BEEC);
        rb_BEEC2 = findViewById(R.id.rb_BEEC2);
    }

    //Titulo
    RadioGroup rg_titulo;
    RadioButton rb_titulo2;

    private void inicializaTitulo() {
        rg_titulo = findViewById(R.id.rg_titulo);
        rb_titulo2 = findViewById(R.id.rb_titulo2);
    }

    //Monitoria
    RadioGroup rg_monitoria;
    RadioButton rb_monitoria2,rb_monitoria3;

    private void inicializaMonitoria() {
        rg_monitoria = findViewById(R.id.rg_monitoria);
        rb_monitoria2 = findViewById(R.id.rb_monitoria2);
        rb_monitoria3 = findViewById(R.id.rb_monitoria3);
    }

    //Ética e outros conselhos
    RadioGroup rg_membro;
    RadioButton rb_membro2,rb_membro3,rb_membro4,rb_membro5,rb_membro6;

    private void inicializaEtica() {
        rg_membro = findViewById(R.id.rg_membro);
        rb_membro2 = findViewById(R.id.rb_membro2);
        rb_membro3 = findViewById(R.id.rb_membro3);
        rb_membro4 = findViewById(R.id.rb_membro4);
        rb_membro5 = findViewById(R.id.rb_membro5);
        rb_membro6 = findViewById(R.id.rb_membro6);
    }

    //Proeficiencia linguas
    RadioGroup rg_testes;
    RadioButton rb_testes2,rb_testes3,rb_testes4,rb_testes5,rb_testes6,rb_testes7;

    private void inicializaTestes() {
        rg_testes = findViewById(R.id.rg_testes);
        rb_testes2 = findViewById(R.id.rb_testes2);
        rb_testes3 = findViewById(R.id.rb_testes3);
        rb_testes4 = findViewById(R.id.rb_testes4);
        rb_testes5 = findViewById(R.id.rb_testes5);
        rb_testes6 = findViewById(R.id.rb_testes6);
        rb_testes7 = findViewById(R.id.rb_testes7);
    }

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_profile);

        inicializaConselho();
        inicializaBEEC();
        inicializaTitulo();
        inicializaMonitoria();
        inicializaEtica();
        inicializaTestes();
        inicializaSocioEconomico();
        inicializaCriteriosAcademicos();
        inicializaProjetoPesquisa();
        inicializaProjetoExtensao();
        inicializaComissao();
        inicializaPoster();
        inicializaPosterExt();
        inicializaParticipacoes();
        inicializaLinguasExt();
        inicializaIdiomas();
        inicializaLinguasIFTM();
        submit = findViewById(R.id.btn_send_check_profile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //conselho
                int id = rg_conselho.getCheckedRadioButtonId();
                if (id == rb_conselho2.getId()){
                    PT = PT + 5;
                }

                //socieconomico
                id = rg_socioEconomico.getCheckedRadioButtonId();
                if (id == rb_socio_economico1.getId()){
                    PT = PT + 20;
                }
                if (id == rb_socio_economico2.getId()){
                    PT = PT + 15;
                }
                if (id == rb_socio_economico3.getId()){
                    PT = PT + 10;
                }

                //academico
                id = rg_criteriosAcademicos.getCheckedRadioButtonId();
                if (id == rb_media1.getId()){
                    PT = PT + 6;
                }
                if (id == rb_media2.getId()){
                    PT = PT + 7;
                }
                if (id == rb_media3.getId()){
                    PT = PT + 8;
                }
                if (id == rb_media4.getId()){
                    PT = PT + 9;
                }
                if (id == rb_media5.getId()){
                    PT = PT + 10;
                }

                //pesquisa
                id = rg_projeto_pesquisa.getCheckedRadioButtonId();
                if (id == rb_ppesquisa1.getId()){
                    PT = PT + 2.5;
                }
                if (id == rb_ppesquisa2.getId()){
                    PT = PT + 5;
                }

                //extensao
                id = rg_projeto_extensao.getCheckedRadioButtonId();
                if (id == rb_pextensao1.getId()){
                    PT = PT + 2.5;
                }
                if (id == rb_pextensao2.getId()){
                    PT = PT + 5;
                }

                //Comissao de eventos
                id = rg_comissao.getCheckedRadioButtonId();
                if (id == rb_comissao_eventos1.getId()){
                    PT = PT + 5;
                }
                if (id == rb_comissao_eventos2.getId()){
                    PT = PT + 10;
                }
                if (id == rb_comissao_eventos3.getId()){
                    PT = PT + 15;
                }
                if (id == rb_comissao_eventos4.getId()){
                    PT = PT + 20;
                }

                //Poster
                id = rg_poster.getCheckedRadioButtonId();
                if (id == rb_poster1.getId()){
                    PT = PT + 1;
                }
                if (id == rb_poster2.getId()){
                    PT = PT + 2;
                }
                if (id == rb_poster3.getId()){
                    PT = PT + 3;
                }
                if (id == rb_poster4.getId()){
                    PT = PT + 4;
                }
                if (id == rb_poster5.getId()){
                    PT = PT + 5;
                }

                //Poster Externo
                id = rg_poster_externo.getCheckedRadioButtonId();
                if (id == rb_poster_extern1.getId()){
                    PT = PT + 1;
                }
                if (id == rb_poster_extern2.getId()){
                    PT = PT + 2;
                }
                if (id == rb_poster_extern3.getId()){
                    PT = PT + 3;
                }
                if (id == rb_poster_extern4.getId()){
                    PT = PT + 4;
                }
                if (id == rb_poster_extern5.getId()){
                    PT = PT + 5;
                }

                //Centro de idiomas
                id = rg_atividadesCI.getCheckedRadioButtonId();
                if (id == rb_atividadesCI1.getId()){
                    PT = PT + 5;
                }
                if (id == rb_atividadesCI2.getId()){
                    PT = PT + 10;
                }
                if (id == rb_atividadesCI3.getId()){
                    PT = PT + 15;
                }
                if (id == rb_atividadesCI4.getId()){
                    PT = PT + 20;
                }

                //aluno CI
                id = rg_linguasIFTM.getCheckedRadioButtonId();
                if (id == rb_linguasIFTM1.getId()){
                    PT = PT + 5;
                }
                if (id == rb_linguasIFTM2.getId()){
                    PT = PT + 10;
                }

                //aluno CI Externo
                id = rg_linguasExt.getCheckedRadioButtonId();
                if (id == rb_linguasExt1.getId()){
                    PT = PT + 5;
                }
                if (id == rb_linguasExt2.getId()){
                    PT = PT + 10;
                }

                //paricipacoes
                id = rg_participacoes_IFTM.getCheckedRadioButtonId();
                if (id == rb_participacoes_IFTM1.getId()){
                    PT = PT + 3;
                }
                if (id == rb_participacoes_IFTM2.getId()){
                    PT = PT + 6;
                }
                if (id == rb_participacoes_IFTM3.getId()){
                    PT = PT + 9;
                }

                //beec
                id = rg_BEEC.getCheckedRadioButtonId();
                if (id == rb_BEEC2.getId()){
                    PT = PT + 5;
                }

                //titulo
                id = rg_titulo.getCheckedRadioButtonId();
                if (id == rb_titulo2.getId()){
                    PT = PT + 5;
                }

                //monitoria
                id = rg_monitoria.getCheckedRadioButtonId();
                if (id == rb_monitoria2.getId()){
                    PT = PT + 5;
                }
                if (id == rb_monitoria3.getId()){
                    PT = PT + 10;
                }

                //membro
                id = rg_membro.getCheckedRadioButtonId();
                if (id == rb_membro2.getId()){
                    PT = PT + 1;
                }
                if (id == rb_membro3.getId()){
                    PT = PT + 2;
                }
                if (id == rb_membro4.getId()){
                    PT = PT + 3;
                }
                if (id == rb_membro5.getId()){
                    PT = PT + 4;
                }
                if (id == rb_membro6.getId()){
                    PT = PT + 5;
                }

                //teste
                id = rg_testes.getCheckedRadioButtonId();
                if (id == rb_testes2.getId()){
                    PT = PT + 5;
                }
                if (id == rb_testes3.getId()){
                    PT = PT + 10;
                }
                if (id == rb_testes4.getId()){
                    PT = PT + 15;
                }
                if (id == rb_testes5.getId()){
                    PT = PT + 20;
                }
                if (id == rb_testes6.getId()){
                    PT = PT + 25;
                }
                if (id == rb_testes7.getId()){
                    PT = PT + 30;
                }

                if (PT >= 60 && PT <= 89){
                    Toast.makeText(getApplicationContext(),"Você está no caminho certo "+ PT + "pontos",Toast.LENGTH_LONG).show();
                }

                if (PT <= 59){
                    Toast.makeText(getApplicationContext(),"Da pra melhorar em, força parceiro " + PT + "pontos",Toast.LENGTH_LONG).show();
                }

                if (PT >= 90){
                    Toast.makeText(getApplicationContext(),"Você já pode começar a competir lol "+ PT + "pontos",Toast.LENGTH_LONG).show();
                }

                DatabaseReference pontuacaoPorfileReference = firebaseDatabase.getReference("Pontuacao").child("pprofile").push();
                String key = pontuacaoPorfileReference.getKey();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();

                Pontuacao pontuacao = new Pontuacao();
                pontuacao.setContent("" + PT);
                pontuacao.setUid(uid);
                pontuacao.setUname(uname);
                pontuacao.setKey(key);

                pontuacaoPorfileReference.setValue(pontuacao).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"fail to add : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

                PT = 0;

                finish();
            }
        });
    }
}

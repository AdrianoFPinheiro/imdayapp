package br.com.adrianofpinheiro.imdayapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.adrianofpinheiro.imdayapp.R
import br.com.adrianofpinheiro.imdayapp.presenter.UsuarioAdapter
import br.com.adrianofpinheiro.imdayapp.model.Usuario
import br.com.adrianofpinheiro.imdayapp.presenter.UsuarioService
import com.maxcruz.reactivePermissions.ReactivePermissions
import com.maxcruz.reactivePermissions.entity.Permission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val manager = supportFragmentManager
    val REQUEST_CODE = 554
    val reacPermissions = ReactivePermissions(this, REQUEST_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Teste para desenvolvedor Android", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Usuario recebe os dados do JSON
        val usuarios: List<Usuario> = UsuarioService().getUsuarios(this)
        //val usuarios = usuarios()
        rvListaUsuarios.adapter = UsuarioAdapter(usuarios, this, {
            Log.i("TAG", "MEU ITEM")
        })

//Grid
//        val layoutManager = GridLayoutManager(this, 2)

//Grade escalonável
//        val layoutManager = StaggeredGridLayoutManager(
//        2, StaggeredGridLayoutManager.VERTICAL)

//Lista na horizontal
//        val layoutManager = LinearLayoutManager(this,
//        LinearLayoutManager.HORIZONTAL, false)

//Lista na vertical
        val layoutManager = LinearLayoutManager(this)
        rvListaUsuarios.layoutManager = layoutManager

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            alert(
                R.string.msg_exit,
                R.string.app_name
            ) {
                positiveButton(R.string.sim) {
                    super.onBackPressed()
                    finish()
                }
                negativeButton(R.string.nao) {
                    // Não confirmou...
                }
            }.show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_item_about -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_ligar_contato -> {
                makeCallPhone()
            }
            R.id.nav_item_compartilhar -> {

                alert(
                    R.string.msg_compartilhar,
                    R.string.app_name
                ) {
                    positiveButton(R.string.sim) {
                        share("https://play.google.com/store/apps/details?id=br.com.adrianofpinheiro.trabalhokotlin" , "www.adrianofpinheiro.info  [Site Adriano F. Pinheiro - 2019]")
                    }
                    negativeButton(R.string.nao) {
                        // Não confirmou...
                    }
                }.show()

            }
            R.id.nav_item_exit -> {
                alert(
                    R.string.msg_exit,
                    R.string.app_name
                ) {
                    positiveButton(R.string.sim) {
                        finish()
                    }
                    negativeButton(R.string.nao) {
                        // Não confirmou...
                    }
                }.show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun usuarios(): List<Usuario> {
        return listOf(
            Usuario(
                "Data mock",
                "Usuario Mock",
                "Url Mock"
            )

        )
    }

    @SuppressLint("CheckResult")
    private fun makeCallPhone(){
        val phone = Permission(
            Manifest.permission.CALL_PHONE,
            R.string.explicacao_permissao,
            true
        )

        val permissions = listOf( phone )

        reacPermissions.observeResultPermissions().subscribe{
                event ->
            if (event.first == Manifest.permission.CALL_PHONE
                && event.second) {

                makeCall("11 96140 0941")
            }
            else if (event.first == Manifest.permission.SEND_SMS
                && event.second) {
                sendSMS("11 96140 0941")
            }
        }

        reacPermissions.evaluate(permissions)
    }

}

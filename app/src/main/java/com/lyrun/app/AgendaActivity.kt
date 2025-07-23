package com.lyrun.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.json.JSONArray

class AgendaActivity : AppCompatActivity() {

    private lateinit var encryptedPrefs: EncryptedSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        // Cria MasterKey para criptografia
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Inicializa EncryptedSharedPreferences
        encryptedPrefs = EncryptedSharedPreferences.create(
            this,
            "agenda_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences

        val etNome = findViewById<EditText>(R.id.etNomeCompromisso)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val listaCompromissos = findViewById<LinearLayout>(R.id.listaCompromissos)

        timePicker.setIs24HourView(true)

        // Carrega compromissos e mostra na UI com botões de remover
        loadCompromissos(listaCompromissos)

        btnAdicionar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val dia = datePicker.dayOfMonth
            val mes = datePicker.month + 1
            val ano = datePicker.year
            val hora = timePicker.hour
            val minuto = timePicker.minute

            if (nome.isNotEmpty()) {
                val compromisso = "• $nome em $dia/$mes/$ano às %02d:%02d".format(hora, minuto)
                addCompromisso(compromisso, listaCompromissos)
                etNome.text.clear()
            } else {
                Toast.makeText(this, "Digite o nome do compromisso!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addCompromisso(compromisso: String, container: LinearLayout) {
        // Atualiza storage criptografado
        val lista = encryptedPrefs.getString("compromissos", null)
        val jsonArray = if (lista != null) JSONArray(lista) else JSONArray()
        jsonArray.put(compromisso)
        encryptedPrefs.edit().putString("compromissos", jsonArray.toString()).apply()

        // Adiciona na UI
        val itemLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 8, 0, 8)
        }

        val textView = TextView(this).apply {
            text = compromisso
            textSize = 18f
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val btnRemover = Button(this).apply {
            text = "Remover"
            setOnClickListener {
                removerCompromisso(compromisso)
                container.removeView(itemLayout)
            }
        }

        itemLayout.addView(textView)
        itemLayout.addView(btnRemover)

        container.addView(itemLayout)
    }

    private fun loadCompromissos(container: LinearLayout) {
        container.removeAllViews()
        val lista = encryptedPrefs.getString("compromissos", null) ?: return
        val jsonArray = JSONArray(lista)
        for (i in 0 until jsonArray.length()) {
            val compromisso = jsonArray.getString(i)
            addCompromisso(compromisso, container)
        }
    }

    private fun removerCompromisso(compromisso: String) {
        val lista = encryptedPrefs.getString("compromissos", null) ?: return
        val jsonArray = JSONArray(lista)
        val novaLista = JSONArray()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getString(i)
            if (item != compromisso) {
                novaLista.put(item)
            }
        }
        encryptedPrefs.edit().putString("compromissos", novaLista.toString()).apply()
    }
}

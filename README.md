# LyRun - App de Assessoria de Corrida

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![License](https://img.shields.io/badge/License-Educational-blue?style=for-the-badge)

LyRun é um aplicativo Android para assessoria de corrida, desenvolvido em Kotlin. O app oferece funcionalidades como agenda de compromissos criptografada, cronômetro intervalado para treinos, e área de doações integrada com PicPay.

---

## Funcionalidades Principais

- **Agenda de Compromissos:**
  - Adicione compromissos com nome, data e hora.
  - Lista de compromissos exibida dinamicamente.
  - Remoção individual de compromissos com atualização segura.
  - Dados armazenados localmente usando `EncryptedSharedPreferences` para garantir privacidade e segurança.

- **Cronômetro com Intervalos:**
  - Timer para treinos intervalados, exemplo: 3x400m com 1 minuto de descanso.
  - Indicação visual da fase atual (Corrida ou Descanso).
  - Vibração e toque sonoro entre fases (opcional, pode ser adaptado para não usar arquivos MP3).

- **Rodapé Personalizado:**
  - Ícone do LinkedIn com link para perfil do criador do app.
  - Ícone do PicPay para doações (redireciona para tela de doação interna).
  - Estilo visual com sombra, cores e espaçamento agradáveis.

- **Criptografia Integrada:**
  - Uso do `EncryptedSharedPreferences` com `MasterKey` para armazenar dados sensíveis da agenda.
  - Garantia de que os dados do usuário fiquem protegidos localmente.

---

## Estrutura do Projeto

- `com.lyrun.app.classe_principal.HomeActivity`: Tela principal com botões para agenda, treino e cronômetro.
- `com.lyrun.app.AgendaActivity`: Tela para adicionar, listar e remover compromissos com criptografia.
- `com.lyrun.cronograma.controller.IntervalTimerViewModel`: ViewModel gerenciando o cronômetro intervalado com LiveData.
- `com.lyrun.cronograma.view.IntervalTimerActivity`: Tela do cronômetro intervalado onde usuário escolhe tempos.
- Layouts XML organizados para tela principal, agenda, rodapé (`footer.xml`), e cronômetro.
- Recursos gráficos para LinkedIn, PicPay, e outros ícones.

---

## Tecnologias e Bibliotecas Usadas

- Kotlin — linguagem principal.
- Android Jetpack (ViewModel, LiveData).
- `EncryptedSharedPreferences` — para armazenamento seguro.
- XML para layouts.
- Android SDK mínimo API 24.
- Gradle Kotlin DSL (`build.gradle.kts`) para configuração.

---

## Como Rodar o Projeto

1. Clone este repositório no Android Studio ou IntelliJ IDEA.
2. Configure o ambiente com SDK API 24 ou superior.
3. Certifique-se de incluir a `MasterKey` no código para criptografia.
4. Importe os recursos gráficos nas pastas `drawable`.
5. Execute o app em um dispositivo ou emulador Android.
6. Utilize a tela principal para navegar entre agenda, treino e cronômetro.

---

## Código Exemplo — Adição e Remoção de Compromissos

```kotlin
// Exemplo simplificado da função que adiciona um compromisso com botão remover:
private fun addCompromisso(compromisso: String, container: LinearLayout) {
    val itemLayout = LinearLayout(this).apply { /* horizontal layout */ }
    val textView = TextView(this).apply { text = compromisso }
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

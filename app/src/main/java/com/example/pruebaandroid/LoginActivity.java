package com.example.pruebaandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //Variables para los componentes visuales
    private EditText Email;
    private EditText Password;
    private Button buttonSignIn;
    //Variables para manejar los datos
    private String email="";
    private String password="";
    //Objeto para la autenticación
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Instancia de firebase y los componentes visuales
        mAuth=FirebaseAuth.getInstance();
        Email=(EditText)findViewById(R.id.TxtEmail);
        Password=(EditText) findViewById(R.id.TxtPassword);
        buttonSignIn=(Button) findViewById(R.id.BtnSignIn);

        //Evento para el botón de inicio de sesión
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Paso los valores a las variables
                email=Email.getText().toString();
                password=Password.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    //llamo a la función de login
                    loginUser();
                }else{
                    Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Función de login
    private void loginUser(){
        //Funcion de logeo con un evento que confirme si la tarea se completo y lleve a la pantalla del perfil
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Paso al perfil
                    startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                    //Cierre de esta actividad
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión. Compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
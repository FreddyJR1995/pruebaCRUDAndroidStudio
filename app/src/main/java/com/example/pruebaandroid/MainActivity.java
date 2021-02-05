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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private EditText Nombre;
private EditText Email;
private EditText Password;
private Button buttonRegister;
private Button buttonLogin;

//variables de los datos a registrar
    private String name="";
    private String email="";
    private String password="";
//objeto de autenticacion
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instancias de los objetos con las variables
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Nombre=(EditText) findViewById(R.id.TxtName);
        Email=(EditText) findViewById(R.id.TxtEmail);
        Password=(EditText) findViewById(R.id.TxtPassword);
        buttonRegister=(Button) findViewById(R.id.BtnRegister);
        buttonLogin=(Button) findViewById(R.id.BtnLogin);

        //Evento del botón de registro
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pasar el valor a las variables
                name=Nombre.getText().toString();
                email=Email.getText().toString();
                password=Password.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        //llamado de la función registro
                        registerUser();
                    }else{
                        Toast.makeText(MainActivity.this, "El password debe tener al menos 6 carateres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Debe Completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passLogin();
            }
        });
    }
    //función de registro
    private void registerUser(){
        //metodo de creación de usuario del objeto de autenticación y evento de validación de la creación
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Pasar los datos al RealtimeDatabse
                    //Creación de un objeto map para pasar los valores
                    Map<String,Object> map= new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("password",password);
                    String id=mAuth.getCurrentUser().getUid();
                    //Envio de los datos y evento de validación del envio
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                //Envio a la ventana de Perfil
                                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Función del paso del lofin
    private void passLogin(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    //Función para cuando no se cierre sesión se inice direatamente en el perfil
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }
    }
}
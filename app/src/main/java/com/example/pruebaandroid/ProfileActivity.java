package com.example.pruebaandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebaandroid.models.Contacto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    //Vector de listado de contactos
    private List<Contacto> listContact=new ArrayList<Contacto>();
    ArrayAdapter<Contacto> ArrayAdapterContacto;
    //variables para los componentes visuales
private Button buttonLogout;
private TextView nombre;
private EditText nameContact;
private EditText emailContact;
private EditText phoneContact;
private ListView ContactsList;
//variables para firebase
private FirebaseAuth mAuth;
DatabaseReference mDatabase;
FirebaseDatabase firebaseDB;
DatabaseReference firebasereference;

Contacto contactSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        buttonLogout=(Button) findViewById(R.id.btnLogout);
        nombre=(TextView) findViewById(R.id.LblName);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        
        nameContact=(EditText) findViewById(R.id.TxtNombreContacto);
        emailContact=(EditText) findViewById(R.id.TxtCorreoContacto);
        phoneContact=(EditText) findViewById(R.id.TxtNumContacto);
        ContactsList=(ListView) findViewById(R.id.lstContactos); 

        listarDatos();
        //Obtener los datos del contacto seleccionado en un nuevo Objeto Contacto
        ContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactSelected=(Contacto) parent.getItemAtPosition(position);
                nameContact.setText(contactSelected.getNombre());
                emailContact.setText(contactSelected.getCorreo());
                phoneContact.setText(contactSelected.getTelefono());
            }
        });
        //Evento para cerrar sesión
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                finish();
            }
        });
        //llamado de la funcion para el nombre del usuario que inicia sesión
        getUserInfo();
    }

    //funcion para traer el listado de contactos de firebase
    private void listarDatos(){
        mDatabase.child("Contactos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listContact.clear();
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Contacto c = objSnapshot.getValue(Contacto.class);
                    listContact.add(c);

                    ArrayAdapterContacto = new ArrayAdapter<Contacto>(ProfileActivity.this, android.R.layout.simple_list_item_1,listContact);
                    ContactsList.setAdapter(ArrayAdapterContacto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Cargar el menú a la barra
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Las opciones de selección del menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nContact=nameContact.getText().toString();
        String cContact=emailContact.getText().toString();
        String pContact=phoneContact.getText().toString();

        switch (item.getItemId()){

            case R.id.icon_add:{
                if(!nContact.isEmpty() && !cContact.isEmpty() && !pContact.isEmpty()) {
                    //Lllevar los datos a un objeto Contacto
                    Contacto c=new Contacto();
                    c.setUid(UUID.randomUUID().toString());
                    c.setNombre(nContact);
                    c.setCorreo(cContact);
                    c.setTelefono(pContact);
                    //Enviar ese objeto con la referencia de firebase
                    mDatabase.child("Contactos").child(c.getUid()).setValue(c);
                    Toast.makeText(this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Ingrese los datos del contacto", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.icon_save:{
                //Crear un objeto Persona
                Contacto c=new Contacto();
                //En base a id seleccionado creo un contacto y lo envio con la referencia de firebase
                c.setUid(contactSelected.getUid());
                c.setNombre(nameContact.getText().toString().trim());
                c.setCorreo(emailContact.getText().toString().trim());
                c.setTelefono(phoneContact.getText().toString().trim());
                mDatabase.child("Contactos").child(c.getUid()).setValue(c);
                Toast.makeText(this, "Contacto Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.icon_delete:{
                //Creo un objeto contacto
                Contacto c=new Contacto();
                //Seteo el id del contacto seleccionado
                c.setUid(contactSelected.getUid());
                //Remuevo el contacto con la referencia de firebase
                mDatabase.child("Contactos").child(c.getUid()).removeValue();
                Toast.makeText(this, "Contacto Borrado", Toast.LENGTH_SHORT).show();
                //Limpio los EditText
                nameContact.setText("");
                emailContact.setText("");
                phoneContact.setText("");
                break;
            }
            default:break;
        }
        return true;
    }

    //Función para obetener los datos del usuario que inicio sesión
    private void getUserInfo(){
        //Obtengeo el id del usuario seleccionado
        String id=mAuth.getCurrentUser().getUid();
        //Lllamamos el evento para que cargue los valores del contacto con dicho id
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Seleccionamos solo el nombre de dicho contacto
                    String name=snapshot.child("name").getValue().toString();
                    //Llevamos a el TextView
                    nombre.setText("Bienvenido: "+name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
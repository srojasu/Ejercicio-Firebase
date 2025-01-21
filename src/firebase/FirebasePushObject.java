package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class FirebasePushObject {

    private FirebaseDatabase firebaseDatabase;

    public static void main(String[] args) {
        FirebasePushObject firebasePush = new FirebasePushObject();

        // Ejemplo: Guardar un nuevo elemento
        Item newItem = new Item();
        newItem.setId(3L);
        newItem.setName("MacBook");
        newItem.setPrice(200.156);
        firebasePush.saveUsingPush(newItem);

        // Ejemplo: Modificar un elemento existente
        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Samsung Galaxy");
        updatedItem.setPrice(200.0);
        firebasePush.updateItem("-OGkjwymJSR0e7P3HW1i", updatedItem);

        // Ejemplo: Eliminar un elemento
        firebasePush.deleteItem("-OGkkCymgBgNfgPbScWa");
    }

    /**
     * Inicialización de Firebase.
     */
    private void initFirebase() {
        try {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://pruebaclase-569ed-default-rtdb.firebaseio.com")
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\sabri\\Downloads\\pruebaclase.json")))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa...");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Guardar un nuevo elemento en Firebase.
     */
    private void saveUsingPush(Item item) {
        if (item != null) {
            initFirebase();
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            DatabaseReference childReference = databaseReference.child("CarpetaPrueba");

            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.push().setValue(item, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de == null) {
                        System.out.println("Registro guardado con éxito!");
                    } else {
                        System.out.println("Error al guardar: " + de.getMessage());
                    }
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Modificar un elemento existente en Firebase.
     */
    private void updateItem(String key, Item updatedItem) {
        initFirebase();
        DatabaseReference databaseReference = firebaseDatabase.getReference("/");
        DatabaseReference itemReference = databaseReference.child("CarpetaPrueba").child(key);

        HashMap<String, Object> updates = new HashMap<>();
        updates.put("id", updatedItem.getId());
        updates.put("name", updatedItem.getName());
        updates.put("price", updatedItem.getPrice());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        itemReference.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    System.out.println("Elemento actualizado correctamente.");
                } else {
                    System.out.println("Error al actualizar: " + error.getMessage());
                }
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminar un elemento de Firebase.
     */
    private void deleteItem(String key) {
        initFirebase();
        DatabaseReference databaseReference = firebaseDatabase.getReference("/");
        DatabaseReference itemReference = databaseReference.child("CarpetaPrueba").child(key);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        itemReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error == null) {
                    System.out.println("Elemento eliminado correctamente.");
                } else {
                    System.out.println("Error al eliminar: " + error.getMessage());
                }
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

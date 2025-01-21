# Ejercicio-Firebase

En este ejercicio el proposito es realizar operaciones en una base de datos, las operaciones que contiene mi codigo (ubicado en la branch master) son: 
 
 FirebasePushObject(): Se usa para agregar elementos en una lista sin sobrescribir los existentes.  
 FirebaseSaveObject(): Se usa con el setValue para reemplazar los datos existentes en esa referencia.  
FirebasePushObject.updatedItem(): Se usa para actualizar un item, para poder hacerlo se necesita la clave unica del nodo en firebase.  
FirebasePushObject.deleteItem(): Se usa para eliminar un item, para poder hacerlo se necesita la clave unica del nodo en firebase.  

Asi estaba la base de datos antes de las operaciones:
![image](https://github.com/user-attachments/assets/edf14954-669d-46d0-af15-a8072600e0e4)
Comprobacion de exito de las operaciones:
![image](https://github.com/user-attachments/assets/610ff69a-24fd-481e-a51d-37d048026d57)
Resultado de la base de datos:
![image](https://github.com/user-attachments/assets/91eb199f-d9d8-4ce0-88a6-2e9382d30828)
Podemos ver que el primer item fue modificado, mientras que otro fue eliminado, y por ultimo otro fue creado.

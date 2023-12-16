package com.example.afusesc.tastynready.model;

    public class UsuarioInfo {
        private String nombre;
        private String email;
        private String uid;
        private String rol;

        // Modificar el constructor para incluir el rol
        public UsuarioInfo(String nombre, String email, String uid, String rol) {
            this.nombre = nombre;
            this.email = email;
            this.uid = uid;
            this.rol = rol;
        }

        // Agrega getters según sea necesario
        public String getNombre() {
            return nombre;
        }

        public String getEmail() {
            return email;
        }

        public String getUid() {
            return uid;
        }

        public String getRol() {
            return rol;
        }
    }


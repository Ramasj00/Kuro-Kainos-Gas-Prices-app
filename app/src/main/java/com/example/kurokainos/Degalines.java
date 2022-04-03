package com.example.kurokainos;

     public  class Degalines{
        String Pavadinimas;
        String Adresas;

        public Degalines(String pavadinimas, String adresas){
            Pavadinimas = pavadinimas;
            Adresas = adresas;
        }

        public String getPavadinimas(){
            return Pavadinimas;
        }

        public void setPavadinimas(String pavadinimas){
            Pavadinimas = pavadinimas;
        }

        public String getAdresas(){
            return Adresas;
        }

        public void setgetAdresas(String adresas){
            Adresas = adresas;
        }

}

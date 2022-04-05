package com.example.kurokainos;

     public  class Degalines{
         int Id;
         String Miestas;
        String Pavadinimas;
        String Adresas;
         String BenzinoKaina;
         String DyzelioKaina;
         String DujuKaina;

        public Degalines(int id,String miestas,String pavadinimas, String adresas, String benzinoKaina, String dyzelioKaina, String dujuKaina){
            Pavadinimas = pavadinimas;
            Adresas = adresas;
            BenzinoKaina = benzinoKaina;
            DyzelioKaina = dyzelioKaina;
            DujuKaina = dujuKaina;
            Miestas = miestas;
            Id=id;
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
        public void setAdresas(String adresas){
            Adresas = adresas;
        }

         public String getDyzelioKaina(){
             return DyzelioKaina;
         }
         public void setDyzelioKainaKaina(String dyzelioKaina){
             DyzelioKaina = dyzelioKaina;
         }

         public String getBenzinoKaina(){
             return BenzinoKaina;
         }
         public void setBenzinoKaina(String benzinoKaina){
             BenzinoKaina = benzinoKaina;
         }

         public String getDujuKaina(){
             return DujuKaina;
         }
         public void setDujuKaina(String dujuKaina){
             DujuKaina = dujuKaina;
         }

         public String getMiestas(){
             return Miestas;
         }
         public void setMiestas(String miestas){
             Miestas = miestas;
         }

         public int getId(){
             return Id;
         }
         public void setId(int id){
             Id = id;
         }
}

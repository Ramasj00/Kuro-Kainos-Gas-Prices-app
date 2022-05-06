package com.example.kurokainos.adapters;

     public  class Degalines{
         int Id;
         String Miestas;
        String Pavadinimas;
        String Adresas;
         String BenzinoKaina;
         String DyzelioKaina;
         String DujuKaina;
         double Latitude;
         double Longtitude;
         String IkelimoData;

        public Degalines(int id,String miestas,String pavadinimas, String adresas, String benzinoKaina, String dyzelioKaina, String dujuKaina,double latitude,double longtitude, String ikelimoData){
            Pavadinimas = pavadinimas;
            Adresas = adresas;
            BenzinoKaina = benzinoKaina;
            DyzelioKaina = dyzelioKaina;
            DujuKaina = dujuKaina;
            Miestas = miestas;
            Id=id;
            Latitude=latitude;
            Longtitude=longtitude;
            IkelimoData=ikelimoData;
        }

         public double getLatitude() {
             return Latitude;
         }

         public void setLatitude(double latitude) {
             Latitude = latitude;
         }

         public double getLongtitude() {
             return Longtitude;
         }

         public void setLongtitude(double longtitude) {
             Longtitude = longtitude;
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

         public String getKuroKaina(String kuroTipas)
         {
             String kaina="";
             switch(kuroTipas){
                 case "benzinas":
                     kaina=BenzinoKaina;
                     break;
                 case "dyzelis":
                     kaina=DyzelioKaina;
                     break;
                 case "dujos":
                     kaina=DujuKaina;
                     break;
             }
             return kaina;
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

         public String getMiestas(){ return Miestas;}
         public void setMiestas(String miestas){
             Miestas = miestas;
         }

         public int getId(){
             return Id;
         }
         public void setId(int id){
             Id = id;
         }

         public String getIkelimoData() { return IkelimoData; }

         public void setIkelimoData(String ikelimoData) {
             IkelimoData = ikelimoData;
         }
     }

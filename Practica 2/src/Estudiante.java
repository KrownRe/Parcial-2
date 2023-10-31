public class Estudiante {
    private int id;
    private String first_name;
    private String last_name;
    private String gender;
    private String career_aspiration;
    private int math_score;

    public Estudiante(int var1, String var2, String var3, String var4, String var5,int var6) {
        this.id = var1;
        this.first_name = var2;
        this.last_name = var3;
        this.gender = var4;
        this.career_aspiration = var5;
        this.math_score = var6;

    }

    public int getId() {
        return this.id;
    }

    public void setId(int var1) {
        this.id = var1;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String var1) {
        this.first_name = var1;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String var1) {
        this.last_name = var1;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String var1) {
        this.gender = var1;
    }

    public String getCareer_aspiration(){
        return this.career_aspiration;
    }

    public void setCareer_aspiration(String var1){
        this.career_aspiration = var1;
    }

    public int getMath_score(){
        return this.math_score;
    }

    public void setMath_score(int var1){
        this.math_score = var1;
    }

    public String toString() {
        return "Estudiante: id= '" + this.id + "', Primer nombre= '" + this.first_name + "', Segundo nombre= '" + this.last_name + "', genero= '" + this.gender + "', Carrera= '" + this.career_aspiration + "', Calificacion matematicas= '" + this.math_score + "'";
    }
}

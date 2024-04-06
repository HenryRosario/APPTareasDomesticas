package network;

import data.Tarea;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface tareaInterface {

    @GET("api/Tareas")
    Call<List<Tarea>> getTodasTareas();

    @POST("api/Tareas")
    Call<Void> enviarTarea(@Body Tarea tarea);

    @PUT("api/Tareas/{id}")
    Call<Void> actualizarTarea(@Path("id")int id, @Body Tarea tarea);


}
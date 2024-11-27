package ar.edu.utn.dds.k3003.clientes.colaboradores;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import ar.edu.utn.dds.k3003.model.controller.dtos.ColaboradorDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface ColaboradoresRetrofitClient {

    @POST("colaboradores/reportarIncidente")
    Call<Void> reportarAlerta(@Body AlertaDTO alertaDTO);

    @GET("colaboradores/{id}")
    Call<ColaboradorDTO> getColaborador(@Path("id") Long id);
}

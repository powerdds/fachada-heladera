package ar.edu.utn.dds.k3003.clientes.colaboradores;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.model.controller.dtos.AlertaDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface ColaboradoresRetrofitClient {

    @POST("colaboradores/reportarIncidente")
    Call<?> reportarAlerta(@Body AlertaDTO alertaDTO);

    @GET("colaboradores/{id}")
    Call<?> getColaborador(@Path("id") Long id);
}

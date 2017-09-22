
package com.example.hesham.moves.Model.ModelVedio;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesVedio {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("resultVedios")
    @Expose
    private List<ResultVedio> resultVedios = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ResultVedio> getResultVedios() {
        return resultVedios;
    }

    public void setResultVedios(List<ResultVedio> resultVedios) {
        this.resultVedios = resultVedios;
    }

}

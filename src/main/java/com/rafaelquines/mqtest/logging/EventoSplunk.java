package com.rafaelquines.mqtest.logging;

import lombok.Data;

@Data
public class EventoSplunk {
    // tipo-evento
    private String tipoEvento;
    private PropriedadesSplunk propriedades;
}

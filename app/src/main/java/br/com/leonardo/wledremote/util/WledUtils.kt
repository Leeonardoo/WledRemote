package br.com.leonardo.wledremote.util;

import br.com.leonardo.wledremote.model.state.Segment
import br.com.leonardo.wledremote.model.state.State

fun getFirstSelectedSegment(state : State?): Segment? {
    return state?.segments?.first { segment -> segment?.selected == true } ?: state?.segments?.first()
}

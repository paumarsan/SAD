const FILES = 6;
const COLUMNES = 7;

function inicialitzarTauler() {
  return Array.from({ length: FILES }, () => Array(COLUMNES).fill(0));
}

function columnaPlena(tauler, columna) {
  return tauler[0][columna] !== 0;
}

function tirarPeça(tauler, col, jugador) {
  for (let fila = FILES - 1; fila >= 0; fila--) {
    if (tauler[fila][col] === 0) {
      tauler[fila][col] = jugador;
      break;
    }
  }
}

function checkVictoria(tauler, jugador) {
  // Check horitzontal
  for (let fila = 0; fila < FILES; fila++) {
    for (let col = 0; col <= COLUMNES - 4; col++) {
      if (
        tauler[fila][col] === jugador &&
        tauler[fila][col + 1] === jugador &&
        tauler[fila][col + 2] === jugador &&
        tauler[fila][col + 3] === jugador
      ) {
        return true;
      }
    }
  }

  // Check vertical
  for (let fila = 0; fila <= FILES - 4; fila++) {
    for (let col = 0; col < COLUMNES; col++) {
      if (
        tauler[fila][col] === jugador &&
        tauler[fila + 1][col] === jugador &&
        tauler[fila + 2][col] === jugador &&
        tauler[fila + 3][col] === jugador
      ) {
        return true;
      }
    }
  }

  // Check diagonal (de Nord-Oest a Sud-Est)
  for (let fila = 0; fila <= FILES - 4; fila++) {
    for (let col = 0; col <= COLUMNES - 4; col++) {
      if (
        tauler[fila][col] === jugador &&
        tauler[fila + 1][col + 1] === jugador &&
        tauler[fila + 2][col + 2] === jugador &&
        tauler[fila + 3][col + 3] === jugador
      ) {
        return true;
      }
    }
  }

  // Check diagonal (de Nord-Est a Sud-Oest)
  for (let fila = 3; fila < FILES; fila++) {
    for (let col = 0; col <= COLUMNES - 4; col++) {
      if (
        tauler[fila][col] === jugador &&
        tauler[fila - 1][col + 1] === jugador &&
        tauler[fila - 2][col + 2] === jugador &&
        tauler[fila - 3][col + 3] === jugador
      ) {
        return true;
      }
    }
  }

  const taulerPle = tauler.every(fila => fila.every(cell => cell !== 0));

  if (taulerPle) {
    return 'tie';
  }

  return false;
}

module.exports = {inicialitzarTauler, columnaPlena, tirarPeça, checkVictoria };

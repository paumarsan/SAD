
const { inicialitzarTauler, columnaPlena, tirarPeça, checkVictoria } = require('./connecta4');

const joc = {
  jugadors: [],
  tauler: inicialitzarTauler(),
  tornJugador: 0,
};


function handleConnection(socket, io) {
  socket.on('unirsePartida', ({nomJugador}) => {
    if (joc.jugadors.length === 0) {
      joc.jugadors.push({ id: socket.id, name: nomJugador });
    }else if (joc.jugadors.length === 1){
      joc.jugadors.push({ id: socket.id, name: nomJugador });
      io.emit('actualitzarJoc' , joc);
    } else {
      socket.emit('missatgeError', 'La sala de joc està plena!');
    }
  });

  socket.on('ferMoviment', ({ columna }) => {
    if (joc && joc.jugadors.some(player => player.id === socket.id) && joc.tornJugador === joc.jugadors.findIndex(player => player.id === socket.id)) {
      if (!columnaPlena(joc.tauler, columna)) {
        tirarPeça(joc.tauler, columna, joc.tornJugador + 1);
        io.emit('actualitzarJoc', joc);
        const result = checkVictoria(joc.tauler, joc.tornJugador + 1);
        if (result === 'tie') {
          io.emit('gameOver', { winner: 'empat' });
        } else if (result) {
          const jugadorGuanyador = joc.jugadors.find(player => player.id === socket.id);
          io.emit('gameOver', { winner: jugadorGuanyador});
        } else {
          joc.tornJugador = 1 - joc.tornJugador; // Canvia torn del jugador
        }
      } else {
        socket.emit('missatgeError', 'La columna està plena!');
      }
    }
  });

  socket.on('reiniciarJoc', () => {
    // Reiniciar la partida
      joc.tornJugador = 0;
      joc.tauler = inicialitzarTauler();
      io.emit('actualitzarJoc', joc);
      io.emit('amagarResultat');

  });
}

module.exports = { handleConnection };
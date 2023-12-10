const { inicialitzarTauler, columnaPlena, tirarPeça, checkVictoria } = require('./connecta4');

const joc = {
  jugadors: [],
  tauler: inicialitzarTauler(),
  tornJugador: 0,
  finpartida: false,
};


function handleConnection(socket, io) {
  socket.on('unirsePartida', ({nomJugador}) => {
    if (joc.jugadors.length === 0) {
      joc.jugadors.push({ id: socket.id, name: nomJugador, color: 1  });
      io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
    }else if (joc.jugadors.length === 1 && joc.jugadors[0].color === 1){
      joc.jugadors.push({ id: socket.id, name: nomJugador, color: 2 });
      io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
      io.emit('actualitzarJoc' , joc);
    }else if (joc.jugadors.length === 1 && joc.jugadors[0].color === 2){
      joc.jugadors.unshift({ id: socket.id, name: nomJugador, color: 1 });
      io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
      io.emit('actualitzarJoc' , joc);
    }else {
      socket.emit('missatgeError', 'La sala de joc està plena!');
    }
  });

  socket.on('ferMoviment', ({ columna }) => {
    if (joc.finpartida === false && joc.jugadors.length === 2 && joc.tornJugador === joc.jugadors.findIndex(player => player.id === socket.id)) {
      if (!columnaPlena(joc.tauler, columna)) {
        tirarPeça(joc.tauler, columna, joc.tornJugador + 1);
        io.emit('actualitzarJoc', joc);
        const result = checkVictoria(joc.tauler, joc.tornJugador + 1);
        if (result === 'tie') {
          joc.finpartida=true;
          io.emit('gameOver', { winner: 'empat' });
        } else if (result) {
          joc.finpartida=true;
          const jugadorGuanyador = joc.jugadors.find(player => player.id === socket.id);
          io.emit('gameOver', { winner: jugadorGuanyador});
        } else {
          joc.tornJugador = 1 - joc.tornJugador; // Canvia torn del jugador
          io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
        }
      } else {
        socket.emit('missatgeError', 'La columna està plena!');
      }
    }
  });

  socket.on('reiniciarJoc', () => {
    // Reiniciar la partida
      joc.finpartida=false;
      joc.tornJugador = 0;
      joc.tauler = inicialitzarTauler();
      io.emit('actualitzarJoc', joc);
      io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
      io.emit('amagarResultat');
  });

  socket.on('disconnect', () => {
    console.log(`Client desconnectat: ${socket.id}`);
    joc.jugadors=joc.jugadors.filter((player) => player.id !== socket.id);
    if(joc.jugadors.length === 0){
      joc.finpartida=false;
      joc.tauler=inicialitzarTauler();
      joc.tornJugador=0;
    }
    io.emit('infoJugadors', {jugadors: joc.jugadors, tornJugador: joc.tornJugador});
  });
}

module.exports = { handleConnection };

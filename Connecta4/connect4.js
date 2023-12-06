const ROWS = 6;
const COLS = 7;

function initializeBoard() {
  return Array.from({ length: ROWS }, () => Array(COLS).fill(0));
}

function isColumnFull(board, column) {
  return board[0][column] !== 0;
}

function dropPiece(board, column, player) {
  for (let row = ROWS - 1; row >= 0; row--) {
    if (board[row][column] === 0) {
      board[row][column] = player;
      break;
    }
  }
}

function checkWin(board, player) {
  // Check horizontal
  for (let row = 0; row < ROWS; row++) {
    for (let col = 0; col <= COLS - 4; col++) {
      if (
        board[row][col] === player &&
        board[row][col + 1] === player &&
        board[row][col + 2] === player &&
        board[row][col + 3] === player
      ) {
        return true;
      }
    }
  }

  // Check vertical
  for (let row = 0; row <= ROWS - 4; row++) {
    for (let col = 0; col < COLS; col++) {
      if (
        board[row][col] === player &&
        board[row + 1][col] === player &&
        board[row + 2][col] === player &&
        board[row + 3][col] === player
      ) {
        return true;
      }
    }
  }

  // Check diagonal (top-left to bottom-right)
  for (let row = 0; row <= ROWS - 4; row++) {
    for (let col = 0; col <= COLS - 4; col++) {
      if (
        board[row][col] === player &&
        board[row + 1][col + 1] === player &&
        board[row + 2][col + 2] === player &&
        board[row + 3][col + 3] === player
      ) {
        return true;
      }
    }
  }

  // Check diagonal (bottom-left to top-right)
  for (let row = 3; row < ROWS; row++) {
    for (let col = 0; col <= COLS - 4; col++) {
      if (
        board[row][col] === player &&
        board[row - 1][col + 1] === player &&
        board[row - 2][col + 2] === player &&
        board[row - 3][col + 3] === player
      ) {
        return true;
      }
    }
  }

  const isBoardFull = board.every(row => row.every(cell => cell !== 0));

  if (isBoardFull) {
    return 'tie';
  }

  return false;
}

module.exports = { initializeBoard, isColumnFull, dropPiece, checkWin };

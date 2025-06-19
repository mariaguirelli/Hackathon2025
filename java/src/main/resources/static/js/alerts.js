window.alertaSucesso = function(mensagem) {
    Swal.fire({
        icon: 'success',
        title: 'Sucesso!',
        text: mensagem,
        confirmButtonColor: '#198754'
    });
}

window.alertaErro = function(mensagem) {
    Swal.fire({
        icon: 'error',
        title: 'Erro!',
        text: mensagem,
        confirmButtonColor: '#dc3545'
    });
}

window.confirmarExclusao = function(callback) {
  Swal.fire({
    title: 'Tem certeza?',
    text: "Esta ação não pode ser desfeita!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sim, excluir',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      callback();
    }
  });
}

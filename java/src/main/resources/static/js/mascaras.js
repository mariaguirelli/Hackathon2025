  $(document).ready(function () {
    $('input[name="cpf"]').mask('000.000.000-00');
  });

   document.addEventListener('DOMContentLoaded', () => {
      document.querySelectorAll('td.cpf').forEach(td => {
        let cpf = td.textContent.trim();
        if (cpf.length === 11) {
          td.textContent = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
        }
      });
    });
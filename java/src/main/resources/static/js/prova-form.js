function adicionarQuestao() {
    const container = document.getElementById('questoes-container');

    const index = contadorQuestoes++;

    const novaQuestaoHTML = `
        <div class="border rounded p-3 mb-3 bg-light questao-item">
            <button type="button" class="btn btn-sm btn-danger mb-2" onclick="removerQuestao(this)">Remover</button>
            <div class="mb-2">
                <label class="form-label">Enunciado</label>
                <textarea name="questoes[${index}].enunciado" class="form-control" required></textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-2">
                    <input type="text" name="questoes[${index}].alternativaA" class="form-control" placeholder="Alternativa A" required/>
                </div>
                <div class="col-md-6 mb-2">
                    <input type="text" name="questoes[${index}].alternativaB" class="form-control" placeholder="Alternativa B" required/>
                </div>
                <div class="col-md-6 mb-2">
                    <input type="text" name="questoes[${index}].alternativaC" class="form-control" placeholder="Alternativa C" required/>
                </div>
                <div class="col-md-6 mb-2">
                    <input type="text" name="questoes[${index}].alternativaD" class="form-control" placeholder="Alternativa D" required/>
                </div>
                <div class="col-md-6 mb-2">
                    <input type="text" name="questoes[${index}].alternativaE" class="form-control" placeholder="Alternativa E" required/>
                </div>
            </div>

            <div class="mb-2">
                <label class="form-label">Resposta Correta</label>
                <select name="questoes[${index}].respostaCorreta" class="form-select" required>
                    <option value="">Selecione</option>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                    <option value="E">E</option>
                </select>
            </div>

            <div class="mb-2">
                <label class="form-label">Valor da Questão</label>
                <input type="number" step="0.01" name="questoes[${index}].valor" class="form-control" required/>
            </div>
        </div>
    `;

    container.insertAdjacentHTML('beforeend', novaQuestaoHTML);
}

function removerQuestao(botao) {
    const questaoDiv = botao.closest('.questao-item');
    if (questaoDiv) {
        const idInput = questaoDiv.querySelector('input[name$=".id"]');
        if (idInput && idInput.value) {
            const campoExcluir = document.getElementById('questoesParaExcluir');
            let ids = campoExcluir.value ? campoExcluir.value.split(',') : [];
            ids.push(idInput.value);
            campoExcluir.value = ids.join(',');
        }
        questaoDiv.remove();
        atualizarIndicesDasQuestoes();
    }
}



function atualizarIndicesDasQuestoes() {
    const questoes = document.querySelectorAll('#questoes-container .questao-item');
    questoes.forEach((questaoDiv, index) => {
        const inputs = questaoDiv.querySelectorAll('input, textarea, select');
        inputs.forEach(input => {
            input.name = input.name.replace(/\[\d+\]/, `[${index}]`);
        });
    });
}





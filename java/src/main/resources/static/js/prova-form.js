    function adicionarQuestao() {
        const container = document.getElementById('questoes-container');

        const div = document.createElement('div');
        div.className = 'border rounded p-3 mb-3';
        div.innerHTML = `
            <div class="mb-2">
                <label>Enunciado</label>
                <textarea name="questoes[${contadorQuestoes}].enunciado" class="form-control" required></textarea>
            </div>
            <div class="row">
                <div class="col-md-6 mb-2"><input type="text" name="questoes[${contadorQuestoes}].alternativaA" class="form-control" placeholder="Alternativa A" required></div>
                <div class="col-md-6 mb-2"><input type="text" name="questoes[${contadorQuestoes}].alternativaB" class="form-control" placeholder="Alternativa B" required></div>
                <div class="col-md-6 mb-2"><input type="text" name="questoes[${contadorQuestoes}].alternativaC" class="form-control" placeholder="Alternativa C" required></div>
                <div class="col-md-6 mb-2"><input type="text" name="questoes[${contadorQuestoes}].alternativaD" class="form-control" placeholder="Alternativa D" required></div>
                <div class="col-md-6 mb-2"><input type="text" name="questoes[${contadorQuestoes}].alternativaE" class="form-control" placeholder="Alternativa E" required></div>
            </div>
            <div class="mb-2">
                <label>Resposta Correta</label>
                <select name="questoes[${contadorQuestoes}].respostaCorreta" class="form-select" required>
                    <option value="">Selecione</option>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                    <option value="E">E</option>
                </select>
            </div>
            <div class="mb-2">
                <label>Valor da Questão</label>
                <input type="number" step="0.01" name="questoes[${contadorQuestoes}].valor" class="form-control" required>
            </div>
        `;

        container.appendChild(div);
        contadorQuestoes++;
    }
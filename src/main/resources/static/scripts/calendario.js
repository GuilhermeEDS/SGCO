const Calendario = {
  calendario: document.getElementById('calendario'),
  modal: document.getElementById('modalAux'),
  conteudoModal: document.getElementById('conteudoModal'),
  modoVisualizacao: 'mes',
  eventos: [],

  createCalendario: function (ano, mes) {
    const cabecalhoCalendario = document.createElement('div');
    cabecalhoCalendario.classList.add('cabecalhoCalendario');
    cabecalhoCalendario.innerHTML = `
      <button class="secondary-btn" onclick="Calendario.toggleView()">Alternar Visualização</button>
      <h2>${this.getTituloCalendario(ano, mes)}</h2>
    `;

    const diasCalendario = document.createElement('div');
    diasCalendario.classList.add('diasCalendario');
    let diasMostrados = 0;
    for (let i = 0; i < 7; i++) {
      diasMostrados++;
      const diaSemana = document.createElement('div');
      diaSemana.classList.add('empty');
      let feira
      switch (i) {
        case 1:
          feira = "Segunda";
          break;
        case 2:
          feira = "Terça";
          break;
        case 3:
          feira = "Quarta";
          break;
        case 4:
          feira = "Quinta";
          break;
        case 5:
          feira = "Sexta";
          break;
        case 6:
          feira = "Sabado";
          break;
        default:
          feira = "Domingo";
      }
      diasCalendario.appendChild(this.createDiaDeSemana(feira));
    }
    if (this.modoVisualizacao === 'mes') {
      const qtdDias = new Date(ano, mes + 1, 0).getDate();
      const primeiroDia = new Date(ano, mes, 1).getDay();

      for (let i = 0; i < primeiroDia; i++) {
        diasMostrados++;
        const diaVazio = document.createElement('div');
        diaVazio.classList.add('empty');
        diasCalendario.appendChild(diaVazio);
      }

      for (let dia = 1; dia <= qtdDias; dia++) {
        diasMostrados++;
        diasCalendario.appendChild(this.createDia(dia));
      }
      
      for (let j = 0; diasMostrados+j < 49 ; j++) {
        const diaVazio = document.createElement('div');
        diaVazio.classList.add('empty');
        diasCalendario.appendChild(diaVazio);
      }
      
    } else if (this.modoVisualizacao === 'semana') {
      const datasDaSemana = this.getDiasDaSemana(ano, mes);
      
      datasDaSemana.forEach(dia => {
        diasCalendario.appendChild(this.createDia(dia));
      });
    }

    this.calendario.innerHTML = '';
    this.calendario.appendChild(cabecalhoCalendario);
    this.calendario.appendChild(diasCalendario);
  },

  createDia: function (dia) {
    const elementoDia = document.createElement('div');
    elementoDia.classList.add('dia');
    elementoDia.textContent = dia;
    const eventos = this.eventos.filter(evento => evento.dia === dia);
    if(eventos.length>0){
      elementoDia.classList.add('expanded');
    }
    if (this.modoVisualizacao === 'semana') {
      if (eventos.length > 0) {
        elementoDia.innerHTML = `<strong>${dia}</strong><br>`;
        elementoDia.classList.add('flex-col');
        elementoDia.classList.add('flex');
        eventos.forEach(evento => {
          elementoDia.innerHTML += `<a class="primary-btn content-center mb-1" href="${"/consulta/"+ evento.id}"> ${evento.horaInicio} - ${evento.horaFim} ${evento.titulo}</a>`;
        });
      }
    }
    if (this.modoVisualizacao === 'mes') {
      elementoDia.addEventListener('click', () => {
        this.expandirDia(dia);
      });
    }
    return elementoDia;
  },

  createDiaDeSemana: function (dia) {
    const elementoDia = document.createElement('div');
    elementoDia.classList.add('empty');
    elementoDia.textContent = dia;
    return elementoDia;
  },

  expandirDia: function (dia) {

    const eventos = this.eventos.filter(evento => evento.dia === dia);
    this.conteudoModal.innerHTML = '';
    if (eventos.length > 0) {
      eventos.forEach(evento => {
        this.conteudoModal.innerHTML += `<a class="primary-btn" href="${"/consulta/"+ evento.id}">${evento.horaInicio} - ${evento.horaFim}: ${evento.titulo}</a>`;
      });
    } else {
      this.conteudoModal.innerHTML = 'Nenhuma consulta marcada para este dia.';
    }

    this.modal.style.display = 'block';

    this.createCalendario(new Date().getFullYear(), new Date().getMonth());
  },

  toggleView: function () {
    this.modoVisualizacao = this.modoVisualizacao === 'mes' ? 'semana' : 'mes';
    const diaAtual = new Date();
    this.createCalendario(diaAtual.getFullYear(), diaAtual.getMonth());
  },

  getTituloCalendario: function (ano, mes) {
    if (this.modoVisualizacao === 'mes') {
      return new Date(ano, mes).toLocaleString('default', { month: 'long', year: 'numeric' });
    } else if (this.modoVisualizacao === 'semana') {
      return `Semana Atual`;
    }
  },

  getDiasDaSemana: function (ano, mes) {
    const dataAtual = new Date();
    const anoAtual = dataAtual.getFullYear();
    const mesAtual = dataAtual.getMonth();
    const diaAtual = dataAtual.getDate();

    const primeiroDiaSemana = new Date(anoAtual, mesAtual, diaAtual - dataAtual.getDay());
    const datasSemana = [];

    for (let i = 0; i < 7; i++) {
      const dia = new Date(anoAtual, mesAtual, primeiroDiaSemana.getDate() + i);
      datasSemana.push(dia.getDate());
    }

    return datasSemana;
  },

  init: function (eventos) {
    if (eventos) {
      this.eventos = eventos;
    }
    const dataAtual = new Date();
    this.createCalendario(dataAtual.getFullYear(), dataAtual.getMonth());
  }
};
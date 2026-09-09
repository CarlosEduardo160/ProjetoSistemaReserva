# Sistema de Reserva

---

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#) 
[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#)
---

Com o intuito de praticar SQL e a conexão entre o banco de dados e código Java, desenvolvi um sistema que simula um programa de agendamento para um restaurante, as reservas devem conter cliente, mesa, quantidade de pessoas e o horário da reserva. 

O projeto foi feito utilizando Java e JDBC puro.

As lógicas de negócio são:
* Uma mesa deve ser ocupada por pelo menos 1 cliente e não deve exceder a quantidade de pessoas definida no registro da mesma.
* Feito a reserva, a mesa deve permanecer ocupada por até 2h.
* Uma reserva só pode ser feita entre 10h-21h (Correspondentes ao horário de inicio e encerramento do atendimento).

---
## Estrutura do Projeto:

### DataBaseConnection

Classe responsável pela conexão com banco de dados, utiliza do padrão de projeto "Singleton" para garantir que haverá uma única conexão com o banco que será utilizada por todo o código.

### Entity

No pacote "Entity" temos as entidades do sistema, são elas:
* Cliente
* Mesa
* Reserva


Cada entidade possui 2 construtores, um com "ID" nos paramêtros e outro sem:

```
    public Cliente(Long idCliente, String nome, String sobrenome) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public Cliente(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
    }
```

Isso acontece porque no banco o ID é uma PK auto incrementável, ou seja, não é da responsabilidade do código atribuir um ID na criação de um objeto entidade. Porem, quando fazemos uma consulta é necessário retornar um objeto, então precisamos de 2 construtores: 

-Um sem ID, para criação da entidade

-E um com ID, para consultas

### DAO (Data Acess Object)

No pacote DAO temos as classes responsáveis pela manipulação e consulta no BD de suas respectivas entidades. 

A única função da classe DAO é se comunicar com o BD, toda validação e lógica de negócio deve ficar em outra classe.

Cada classe possui os seguintes métodos:
* Cadastrar uma entidade no BD
* Listar tudo que estiver na respectiva tabela
* Buscar uma entidade por ID
* Alterar os dados de uma entidade (Reserva não possui esse método)
* Excluir entidade do BD

### Service

No pacote Service temos as classes responsáveis pelas validações e lógica de negócio, é aqui onde os dados inseridos pelo usuário são validados e tratados para garantir que as consultas na classe DAO sejam feitas corretamente.

Para comunicação entre as camadas, as classes Service são dependentes de suas respectivas DAO para chamar seus métodos e enviar os dados necessários para a manipulação do BD.
```
    public ReservaService(ReservaDAO reservaDAO, ClienteService clienteService, MesaService mesaService) {
        this.reservaDAO = reservaDAO;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }    
```

Gostaria de destacar 2 lógicas de negócios presentes na __ServiceCliente__ e __ServiceReserva__:

__ServiceCliente__ possui uma validação Regex no cadastro e alteração dos dados do cliente para garantir que o nome seja inserido corretamente, somente com letras:

(mesaService possui o mesmo tipo de validação regex, para garantir que o numero da mesa seja de fato um número)

```
    public void criarCliente(String nome, String sobrenome){
        String regexNome = "^[a-zA-ZÀ-ÿ\\s]+$";

        if(nome.matches(regexNome) && sobrenome.matches(regexNome)) {
            Cliente novoCliente = new Cliente(nome, sobrenome);
            clienteDAO.cadastrarCliente(novoCliente);
        } else {
            throw  new IllegalArgumentException("Nome inválido, use apenas letras.");
        }
    }
```

__ServiceReserva__ possui 2 validações para o horário da reserva:

```
    public void validarHorarios(LocalDateTime horarioReserva) {
        LocalTime horarioInicioAtendimento = LocalTime.of(10, 0, 0);
        LocalTime horarioFimAtendimento = LocalTime.of(21, 0, 0);

        if (horarioReserva.toLocalTime().isBefore(horarioInicioAtendimento)) {
            throw new IllegalArgumentException("Fora do horário de funcionamento.");
        }

        LocalTime reservaConvertida = horarioReserva.plusHours(1).toLocalTime();
        boolean verifica = reservaConvertida.isAfter(horarioFimAtendimento);
        if (verifica) {
            throw new IllegalArgumentException("A reserva deve ser marcada em até 1h antes do horário de fechamento.");
        }
    }
```

Este primeiro método serve para verificar se a data da reserva está dentro do horário de atendimento (10h-21h) do estabelecimento. Também verifica se ela está sendo feito ao menos 1h antes do fim do atendimento

```
    public boolean mesaDisponivel(LocalDateTime inicioNovaReserva, List<Reserva> reservaExistentes){
        LocalDateTime fimNovaReserva = inicioNovaReserva.plusHours(2);

        for (Reserva reserva : reservaExistentes){
            LocalDateTime inicioReservaExistente = reserva.getDataReserva();
            LocalDateTime fimReservaExistente = inicioReservaExistente.plusHours(2);

            boolean semConflito = !fimReservaExistente.isAfter(inicioNovaReserva) || !fimNovaReserva.isAfter(inicioReservaExistente);

            if(!semConflito){
                return false;
            }
        }
        return true;
    }
```

Este segundo método serve para garantir que o período máximo de uma reserva (2h) esteja sendo respeitado. 

Em Java não temos uma forma direta de comparar "Esse horário está entre A e B?" ou de forma mais técnica: "horario.isBeforeOrEqual" então precisamos aplicar uma lógica de inversão:

A.isAfter(B) retorna "verdadeiro" __somente__ se o horário A estiver __após__ B.

Assim como A.isBefore(B) retorna "verdadeiro" __somente__ se A estiver __antes__ de B.

Mas quando invertemos a lógica, e vira !A.isAfter(B)  código passa a retornar "verdadeiro" se A estiver antes __ou__ ao mesmo tempo de B.


A linha:
```
 if(!semConflito){
                return false;
            }
        }
        return true;
```

Também aplica uma inversão, se for ao contrário de não haver conflito, ou seja, há conflito, retorna false para que a verificação continue.

### Main e ReservaUi

Por fim temos as classes "Main" e "Ui"

A classe main está responsável pelas injeções de dependência (como mencionadas anteriormente) e da inicialização do sistema. 

A classe Ui é unicamente responsável pela exibição e coleta de informações, nenhuma lógica deve ser trabalhada aqui, apenas nas services. 

* Adendo:  
```
if(clientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
                return;
            }
```
apesar de ser uma condicional, trata-se de uma "Lógica de apresentação", uma lógica que deve definir como algo será exibido, e não como será tratado.

* Limpeza de cache no scanner:
```
System.out.print("Digite o ID do cliente que deseja atualizar: ");
        Long id = entrada.nextLong();
        entrada.nextLine();
```
Sempre que lemos um tipo numérico com scanner, ele acaba absorvendo um espaço vazio que pode vir a prejudicar uma entrada textual, então após cada entrada numérica é uma boa prática consumir esse "espaço vazio", para que uma próxima entrada textual não acabe enviando uma string em branco para outras camadas.
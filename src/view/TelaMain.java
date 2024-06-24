package view;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;

import DAO.CategoriaDAO;
import DAO.ResiduoDAO;
import model.Categoria;
import model.Residuo;

public class TelaMain {

    public static void main(String[] args) {

        List<Residuo> residuos = ResiduoDAO.getResiduos();
        List<Categoria> categorias = CategoriaDAO.getCategorias();
        UIManager.put("OptionPane.minimumSize", new DimensionUIResource(700, 400));
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.messageAreaBorder", BorderFactory.createEmptyBorder(100, 0, 100, 0));
        UIManager.put("OptionPane.messageFont", new FontUIResource(null, 0, 20));

        Integer opcao = -1;

        do {

            opcao = mostrarMenu();

            switch (opcao) {
                case 1:
                    cadastrarCategoria();
                    break;

                case 2:
                    categorias = CategoriaDAO.getCategorias();
                    cadastrarResiduo(residuos, categorias);
                    break;

                case 3:
                    categorias = CategoriaDAO.getCategorias();
                    residuos = ResiduoDAO.getResiduos();
                    listarResiduos(residuos, categorias);
                    break;

                case 4:
                    categorias = CategoriaDAO.getCategorias();
                    listarCategoria(categorias);
                    break;

                case 5:
                    categorias = CategoriaDAO.getCategorias();
                    residuos = ResiduoDAO.getResiduos();
                    excluirResiduos(residuos, categorias);
                    break;

                case 6:
                    categorias = CategoriaDAO.getCategorias();
                    residuos = ResiduoDAO.getResiduos();
                    excluirCategoria(categorias, residuos);
                    break;

                case 7:
                    residuos = ResiduoDAO.getResiduos();
                    incrementarQuantidade(residuos);
                    ;
                    break;

                case 8:
                    residuos = ResiduoDAO.getResiduos();
                    decrementarQuantidade(residuos);
                    break;

                case 9:
                    categorias = CategoriaDAO.getCategorias();
                    atualizarCategoria(categorias);
                    break;

                case 10:
                    residuos = ResiduoDAO.getResiduos();
                    atualizarResiduo(residuos);
                    break;

                case 11:
                    residuos = ResiduoDAO.getResiduos();
                    categorias = CategoriaDAO.getCategorias();
                    gerarRelatorio(residuos,categorias);
                    break;

                default:
                    break;
            }

        } while (opcao != 0);

    }

    public static Integer mostrarMenu() {

        int opcao = 0;

        String texto = "\nDigite a opção desejada: " +
                "\n1 - Cadastrar Categoria 📝" +
                "\n2 - Cadastrar Resíduo 📝" +
                "\n3 - Listar Resíduos 📋" +
                "\n4 - Listar Categorias 📋" +
                "\n5 - Excluir Resíduo ❌" +
                "\n6 - Excluir Categoria ❌" +
                "\n7 - Adicionar estoque ➕" +
                "\n8 - Retirar estoque ➖" +
                "\n9 - Atualizar Categoria ⇪" +
                "\n10 - Atualizar Residuo ⇪" +
                "\n11 - Gerar Relatório 📈" +
                "\n0 - Finalizar\n\n\n\n\n";

        opcao = Integer.valueOf(JOptionPane.showInputDialog(null, texto, "Menu", JOptionPane.INFORMATION_MESSAGE));

        return opcao;

    }

    // Método para cadastrar categorias no banco de dados
    public static void cadastrarCategoria() {

        String nome = JOptionPane.showInputDialog(null, "Digite o nome da categoria: ", "Cadastrar Categoria",
                JOptionPane.INFORMATION_MESSAGE);

        if (!nome.isEmpty()) {
            CategoriaDAO.cadastrarCategoria(nome);

        }

    }

    // Método para listar categorias cadastradas no banco de dados
    public static void listarCategoria(List<Categoria> categoriasCadastradas) {

        String texto = "Categorias cadastradas:";
        if (categoriasCadastradas.isEmpty()) {
            texto = "Nenhuma Categoria cadastrada";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);

        } else {
            for (Categoria categoria : categoriasCadastradas) {
                texto += "\n---------------------------";
                texto += "\n Nome: " + categoria.nome + " - id: " + categoria.id;
            }
            JOptionPane.showMessageDialog(null, texto, "Lista de Categorias", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para cadastrar residuos no banco de dados
    public static void cadastrarResiduo(List<Residuo> residuos, List<Categoria> categorias) {

        String nome = "";
        int idCategoriaDigitada = 0;
        String texto = "Categorias cadastradas: ";
        Categoria categoriaDesejada = null;

        if (categorias.isEmpty()) {
            texto = "Nenhuma categoria cadastrada.";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            nome = JOptionPane.showInputDialog(null,"Digite o nome do resíduo","Cadastrar resíduo",
            JOptionPane.INFORMATION_MESSAGE);
            for (Categoria categoria : categorias) {
                texto += "\n---------------------------";
                texto += "\nNome: " + categoria.nome + " -  Id: " + categoria.id;
            }
            texto += "\n\nDigite o id da categoria que o resíduo será vinculado: ";
            idCategoriaDigitada = Integer.valueOf(JOptionPane.showInputDialog(null,texto,"Cadastrar resíduo",
            JOptionPane.INFORMATION_MESSAGE));

            ResiduoDAO.cadastrarResiduo(nome, idCategoriaDigitada);
        }
    }

    // Método para listar meus residuos cadastrados no banco de dados
    public static void listarResiduos(List<Residuo> residuosCadastrados, List<Categoria> categoriasCadastradas) {

        String texto = "Residuos cadastrados:";
        if (residuosCadastrados.isEmpty()) {
            texto = "Nenhum Residuo cadastrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);

        } else {
            for (Residuo residuo : residuosCadastrados) {
                texto += "\n------------------------------------------------";
                texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " + residuo.getValorId();
                for (Categoria categoria : categoriasCadastradas) {
                    if (categoria.id == residuo.idCategoria) {
                        texto += " - Categoria Pertencente: " + categoria.nome;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, texto, "Lista de Resíduos", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    // Metodo para excluir um residuo do banco de dados
    public static void excluirResiduos(List<Residuo> residuosCadastrados, List<Categoria> categoriasCadastradas) {

        int idDesejado = 0;
        String mensagemDeConfirmação = "Deseja mesmo excluir o resíduo: ";
        String residuoEscolhido = "";
        String texto = "Residuos cadastrados:";
        if (residuosCadastrados.isEmpty()) {
            texto = "Nenhum Residuo cadastrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Residuo residuo : residuosCadastrados) {
                texto += "\n---------------------------";
                texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " + residuo.getValorId();
                for (Categoria categoria : categoriasCadastradas) {
                    if (categoria.id == residuo.idCategoria) {
                        texto += " - Categoria Pertencente: " + categoria.nome;
                    }
                }
            }
            texto += "\n\nDigite o número do id do residuo que você deseja excluir:";
            idDesejado = Integer
                    .valueOf(JOptionPane.showInputDialog(null, texto, "Excluir resíduo", JOptionPane.WARNING_MESSAGE));
            for (Residuo residuo : residuosCadastrados) {
                if (residuo.id == idDesejado) {
                    residuoEscolhido = residuo.nome;
                    break;
                }
            }
            mensagemDeConfirmação += residuoEscolhido;

            int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                    "Excluir Resíduo", JOptionPane.YES_NO_OPTION));
            if (confirmar == 0) {
                ResiduoDAO.excluirResiduo(idDesejado);
            } else {
                JOptionPane.showMessageDialog(null, "Operação de remoção de resíduo cancelada pelo usuário. ❌",
                        "Cancelamento", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public static void excluirCategoria(List<Categoria> categoriasCadastradas, List<Residuo> residuosCadastrados) {

        int idDesejado = 0;
        String mensagemDeConfirmação = "Deseja mesmo excluir a categoria: ";
        Categoria categoriaEscolhida = new Categoria();
        List<Residuo> residuos = new ArrayList<Residuo>();
        String texto = "";

        if (categoriasCadastradas.isEmpty()) {
            texto = "Nenhuma categoria cadastrada!";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Categoria categorias : categoriasCadastradas) {
                texto += "\n----------------------";
                texto += "\nNome: " + categorias.nome + " - id: " + categorias.id;
            }
            texto += "\n\nDigite o id da categoria que você deseja remover: ";
            idDesejado = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Remover Categoria", JOptionPane.WARNING_MESSAGE));
            for (Categoria categoria : categoriasCadastradas) {
                if (categoria.id == idDesejado) {
                    categoriaEscolhida = categoria;
                    break;
                }
            }
            for (Residuo residuo : residuosCadastrados) {
                if (residuo.idCategoria == idDesejado) {
                    residuos.add(residuo);
                }
            }

            if (residuos.isEmpty()) {
                mensagemDeConfirmação += categoriaEscolhida.nome;
                int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                        "Remover Categoria", JOptionPane.YES_NO_OPTION));
                if (confirmar == 0) {
                    CategoriaDAO.excluirCategoria(idDesejado);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação de remoção de categoria cancelada pelo usuário. ❌",
                            "Cancelamento", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String textoResiduos = "";
                for (Residuo residuo : residuos) {
                    textoResiduos += residuo.nome + ", ";
                }
                texto = "Impossivél realizar essa ação pois o(s) resíduo(s): " + textoResiduos
                        + "estão cadastrados nessa categoria: (" + categoriaEscolhida.nome + ")";
                JOptionPane.showMessageDialog(null, texto, "Cancelamento", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void incrementarQuantidade(List<Residuo> residuosCadastrados) {

        int idDesejado = 0;
        double qtd = 0;
        String mensagemDeConfirmação = "Deseja mesmo adicionar : ";
        int idResiduoEscolhido = 0;
        String nomeResiduoescolhido = "";
        String texto = "Residuos cadastrados:";
        if (residuosCadastrados.isEmpty()) {
            texto = "Nenhum Residuo cadastrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Residuo residuo : residuosCadastrados) {
                texto += "\n---------------------------";
                texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " + residuo.getValorId();

            }
            texto += "\n\nDigite o número do id do residuo que você deseja adicionar ao estoque:";
            idDesejado = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Adicionar ao estoque", JOptionPane.INFORMATION_MESSAGE));
            texto = "\n\nDigite o número da quantidade de residuo que você deseja adicionar ao estoque:";
            qtd = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Adicionar ao estoque", JOptionPane.INFORMATION_MESSAGE));
            for (Residuo residuo : residuosCadastrados) {
                if (residuo.id == idDesejado) {
                    idResiduoEscolhido = residuo.id;
                    nomeResiduoescolhido = residuo.nome;
                    break;
                }
            }
            mensagemDeConfirmação += qtd + " de quantidade" + " para o resíduo: " + nomeResiduoescolhido;

            int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                    "Adicionar resíduo ao estoque", JOptionPane.YES_NO_OPTION));
            if (confirmar == 0) {
                ResiduoDAO.incrementarEstoque(qtd, idResiduoEscolhido);
            } else {
                JOptionPane.showMessageDialog(null, "Operação de alterar estoque foi cancelada pelo usuário. ❌",
                        "Cancelamento", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public static void decrementarQuantidade(List<Residuo> residuosCadastrados) {

        int idDesejado = 0;
        double qtd = 0;
        String mensagemDeConfirmação = "Deseja mesmo retirar : ";
        int idResiduoEscolhido = 0;
        String nomeResiduoescolhido = "";
        String texto = "Residuos cadastrados:";
        if (residuosCadastrados.isEmpty()) {
            texto = "Nenhum Residuo cadastrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Residuo residuo : residuosCadastrados) {
                texto += "\n---------------------------";
                texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " + residuo.getValorId();

            }
            texto += "\n\nDigite o número do id do residuo que você deseja retirar ao estoque:";
            idDesejado = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Retirar ao estoque", JOptionPane.INFORMATION_MESSAGE));
            texto = "\n\nDigite o número da quantidade de residuo que você deseja retirar ao estoque:";
            qtd = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Retirar ao estoque", JOptionPane.INFORMATION_MESSAGE));
            for (Residuo residuo : residuosCadastrados) {
                if (residuo.id == idDesejado) {
                    idResiduoEscolhido = residuo.id;
                    nomeResiduoescolhido = residuo.nome;
                    break;
                }
            }
            mensagemDeConfirmação += qtd + " de quantidade" + " para o resíduo: " + nomeResiduoescolhido;

            int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                    "Retirar ao estoque", JOptionPane.YES_NO_OPTION));
            if (confirmar == 0) {
                ResiduoDAO.decrementarEstoque(qtd, idResiduoEscolhido);
            } else {
                JOptionPane.showMessageDialog(null, "Operação de alterar estoque foi cancelada pelo usuário. ❌",
                        "Cancelamento", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public static void atualizarCategoria(List<Categoria> categoriasCadastradas) {
        int idDesejado = 0;
        String mensagemDeConfirmação = "Deseja mesmo atualizar";
        String nomeCategoriaEscolhida = "";
        String novoNome = "";
        String texto = "Categorias cadastradas:";

        if (categoriasCadastradas.isEmpty()) {
            texto = "Nenhuma Categoria cadastrada";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Categoria categoria : categoriasCadastradas) {
                texto += "\nNome: " + categoria.nome + " ID: " + categoria.id;
            }
            texto += "\nDigite o ID da categoria que você deseja atualizar:";
            idDesejado = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Atualizar Categoria", JOptionPane.INFORMATION_MESSAGE));

            for (Categoria categoria : categoriasCadastradas) {
                if (categoria.id == idDesejado) {
                    nomeCategoriaEscolhida = categoria.nome;                    
                }
                
            }
            texto = "Digite o novo nome da categoria:";
            novoNome = JOptionPane.showInputDialog(texto);

            mensagemDeConfirmação += " o nome: " + nomeCategoriaEscolhida + " para: " + novoNome + " ?";

            int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                    "Atualizar Resíduo", JOptionPane.YES_NO_OPTION));

            if (confirmar == 0 ) {
                CategoriaDAO.atualizarCategoria(novoNome, idDesejado);
            }else{
                JOptionPane.showMessageDialog(null, "Operação de atualizar categoria foi cancelada pelo usuário. ❌",
                "Cancelamento", JOptionPane.ERROR_MESSAGE);
            }




            
        }

    }

    public static void atualizarResiduo(List<Residuo> residuosCadastrados) {
        int idDesejado = 0;
        String mensagemDeConfirmação = "Deseja mesmo atualizar";
        String nomeResiduoEscolhido = "";
        String novoNome = "";
        String texto = "Resíduos cadastrados:";

        if (residuosCadastrados.isEmpty()) {
            texto = "Nenhum Resíduo cadastrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Residuo residuo : residuosCadastrados) {
                texto += "\nNome: " + residuo.nome + " ID: " + residuo.id;
            }
            texto += "\nDigite o ID do Resíduo que você deseja atualizar:";
            idDesejado = Integer.valueOf(
                    JOptionPane.showInputDialog(null, texto, "Atualizar resíduo", JOptionPane.INFORMATION_MESSAGE));

            for (Residuo residuo : residuosCadastrados) {
                if (residuo.id == idDesejado) {
                    nomeResiduoEscolhido = residuo.nome;                    
                }
                
            }
            texto = "Digite o novo nome do resíduo:";
            novoNome = JOptionPane.showInputDialog(null,texto,"Atualizar resíduo", JOptionPane.INFORMATION_MESSAGE);

            mensagemDeConfirmação += " o nome: " + nomeResiduoEscolhido + " para: " + novoNome + " ?";

            int confirmar = Integer.valueOf(JOptionPane.showConfirmDialog(null, mensagemDeConfirmação,
                    "Atualizar Resíduo", JOptionPane.YES_NO_OPTION));

            if (confirmar == 0 ) {
                ResiduoDAO.atualizarCategoria(novoNome, idDesejado);
            }else{
                JOptionPane.showMessageDialog(null, "Operação de atualizar resíduo foi cancelada pelo usuário. ❌",
                "Cancelamento", JOptionPane.ERROR_MESSAGE);
            }




            
        }

    }

     // Método para gerar relatorio do sistema
     public static void gerarRelatorio(List<Residuo> residuosCadastrados, List<Categoria> categoriasCadastradas) {

        String texto = "Categorias cadastradas: \n";
        if (residuosCadastrados.isEmpty() && categoriasCadastradas.isEmpty()) {
            texto = "Nenhum Dado encontrado";
            JOptionPane.showMessageDialog(null, texto, "Erro", JOptionPane.ERROR_MESSAGE);

        } else {
            for (Categoria categoria : categoriasCadastradas) {
                texto += "\n Nome: " + categoria.getNomeCategoria() + " - id: " + categoria.getIdCategoria();
            }
            texto += "\n---------------------------------------------------------------------";
            texto += "\nResíduos cadastrados: \n";
            for (Residuo residuo : residuosCadastrados) {
                texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " + residuo.getValorId() + " - QTD: " + residuo.getValorQtd() + " KG";
                for (Categoria categoria : categoriasCadastradas) {
                    if (categoria.id == residuo.idCategoria) {
                        texto += " - Categoria Pertencente: " + categoria.nome;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, texto, "Relatório", JOptionPane.INFORMATION_MESSAGE);

        }

    }


}

// public static void listarCategoria(List<Categoria> categoriasCadastradas) {

// String texto = "Categorias cadastradas:";
// if (categoriasCadastradas.isEmpty()) {
// texto += "\nNenhuma Categoria cadastrada";
// } else {
// for (Categoria categoria : categoriasCadastradas) {
// texto += "\n---------------------------";
// texto += "\n Nome: " + categoria.nome + " id: " + categoria.id;
// }
// }

// JOptionPane.showMessageDialog(null, texto);

// }

// public static void cadastrarCategoria(List<Categoria> categorias) {

// String nome = JOptionPane.showInputDialog("Digite o nome da categoria");
// String idDigitado = JOptionPane.showInputDialog("Digite o id da categoria");
// int id = 0;

// if (!idDigitado.isEmpty()) {
// id = Integer.valueOf(idDigitado);

// } else {

// }

// Categoria newCategoria = new Categoria(nome, id);
// System.out.println(newCategoria);

// categorias.add(newCategoria);

// }

// public static void cadastrarResiduo(List<Residuo> residuos, List<Categoria>
// categorias) {

// String nome = JOptionPane.showInputDialog("Digite o nome do resíduo");
// String idDigitado = JOptionPane.showInputDialog("Digite o id do resíduo");
// String texto = "Categorias cadastradas: ";
// Categoria categoriaDesejada = null;
// int id = 0;

// if (!idDigitado.isEmpty()) {
// id = Integer.valueOf(idDigitado);

// } else {
// JOptionPane.showMessageDialog(null, "Nada Digitado.");
// }

// if (categorias.isEmpty()) {
// texto += "\n Nenhuma categoria cadastrada.";
// } else {
// for (Categoria categoria : categorias) {
// texto += "\n---------------------------";
// texto += "\nNome: " + categoria.nome + " - Id: " + categoria.id;
// }
// texto += "\nDigite o id da categoria desejada: ";
// String idCategoriaDigitada = JOptionPane.showInputDialog(texto);
// Integer idCategoria = Integer.valueOf(idCategoriaDigitada);

// for (Categoria categoria : categorias) {
// if (categoria.id == idCategoria) {
// categoriaDesejada = categoria;

// }
// }

// }

// Residuo newResiduo = new Residuo(categoriaDesejada, nome, id);
// residuos.add(newResiduo);

// }

// public static void listarResiduos(List<Residuo> residuosCadastrados) {

// String texto = "Residuos cadastrados:";
// if (residuosCadastrados.isEmpty()) {
// texto += "\nNenhum Residuo cadastrado";
// } else {
// for (Residuo residuo : residuosCadastrados) {
// texto += "\n---------------------------";
// texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " +
// residuo.getValorId() + " - Categoria: "
// + residuo.getValorCategoria();
// }
// }

// JOptionPane.showMessageDialog(null, texto);

// }

// public static void excluirResiduos(List<Residuo> residuosCadastrados) {

// int idDesejado = 0;
// int control = 0;

// String texto = "Residuos cadastrados:";
// if (residuosCadastrados.isEmpty()) {
// texto += "\nNenhum Residuo cadastrado";
// } else {
// for (Residuo residuo : residuosCadastrados) {
// texto += "\n---------------------------";
// texto += "\n Nome: " + residuo.getNomeResiduo() + " - id: " +
// residuo.getValorId() + " - Categoria: "
// + residuo.getValorCategoria();
// }
// texto += "\nDigite o número do id do residuo que você deseja excluir:";
// idDesejado = Integer.valueOf(JOptionPane.showInputDialog(null, texto));

// Residuo residuoRemover = null;

// for (Residuo residuo : residuosCadastrados) {

// if (residuo.getValorId() == idDesejado) {
// residuoRemover = residuo;
// break;

// }
// control++;

// }

// if (residuoRemover != null) {
// residuosCadastrados.remove(residuoRemover);

// }
// }

// }

// public static void excluirCategoria(List<Categoria> categoriasCadastradas,
// List<Residuo> residuosCadastrados) {

// int idDesejado = 0;
// int control = 0;

// String texto = "Categorias cadastradas:";
// if (categoriasCadastradas.isEmpty()) {
// texto += "\nNenhuma Categoria cadastrada";
// } else {
// for (Categoria categorias : categoriasCadastradas) {
// texto += "\n---------------------------";
// texto += "\n Nome: " + categorias.getNomeCategoria() + " - id: " +
// categorias.getIdCategoria()
// + " - Categoria: ";
// }
// texto += "\nDigite o número do id da categoria que você deseja excluir:";
// idDesejado = Integer.valueOf(JOptionPane.showInputDialog(null, texto));

// List<Residuo> residuosEncontrados = new ArrayList<Residuo>();

// for (Residuo residuo : residuosCadastrados) {
// if (residuo.getValorId() == idDesejado) {
// residuosEncontrados.add(residuo);
// }
// }

// if (residuosEncontrados.isEmpty()) {

// Categoria categoriaRemover = null;

// for (Categoria categorias : categoriasCadastradas) {

// if (categorias.getIdCategoria() == idDesejado) {
// categoriaRemover = categorias;
// break;

// }
// control++;

// }

// if (categoriaRemover != null) {
// categoriasCadastradas.remove(categoriaRemover);
// JOptionPane.showMessageDialog(null, "Categoria removida com sucesso");

// }
// } else{
// JOptionPane.showMessageDialog(null, "Impossivel realizar a remoção da
// categoria");
// }
// }

// }

// }

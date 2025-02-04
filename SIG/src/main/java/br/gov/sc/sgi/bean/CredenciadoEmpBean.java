package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.CredenciadoAlvaraDAO;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpObsDAO;
import br.gov.sc.sgi.dao.CredenciadoPortariaDAO;
import br.gov.sc.sgi.dao.CredenciadoSGPEDAO;
import br.gov.sc.sgi.dao.CredencialEmpTipoDAO;
import br.gov.sc.sgi.dao.CredencialEndAdicDAO;
import br.gov.sc.sgi.dao.CredencialEndAdicHistDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoCredDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoCredHistDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoPropDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoPropHistDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoVeicDAO;
import br.gov.sc.sgi.dao.CredencialRelacaoVeicHistDAO;
import br.gov.sc.sgi.dao.CredencialStatusDAO;
import br.gov.sc.sgi.dao.CredencialVeiculoDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.dao.PessoaJuridicaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoAlvara;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoEmpHist;
import br.gov.sc.sgi.domain.CredenciadoEmpObs;
import br.gov.sc.sgi.domain.CredenciadoPortaria;
import br.gov.sc.sgi.domain.CredenciadoSGPE;
import br.gov.sc.sgi.domain.CredencialEmpTipo;
import br.gov.sc.sgi.domain.CredencialEndAdic;
import br.gov.sc.sgi.domain.CredencialEndAdicHist;
import br.gov.sc.sgi.domain.CredencialRelacaoCred;
import br.gov.sc.sgi.domain.CredencialRelacaoCredHist;
import br.gov.sc.sgi.domain.CredencialRelacaoProp;
import br.gov.sc.sgi.domain.CredencialRelacaoPropHist;
import br.gov.sc.sgi.domain.CredencialRelacaoVeic;
import br.gov.sc.sgi.domain.CredencialRelacaoVeicHist;
import br.gov.sc.sgi.domain.CredencialStatus;
import br.gov.sc.sgi.domain.CredencialVeiculo;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredenciadoEmpBean implements Serializable {

	private PessoaJuridica empresa;
	private PessoaFisica pessoa;

	private CredenciadoEmp credenciado;
	private CredenciadoEmp credenciadoDaBusca;

	private CredenciadoEmpHist credenciadoEmpHist;

	private CredenciadoSGPE SGPE;
	private CredenciadoEmpObs obs;
	private CredenciadoPortaria portaria;
	private CredenciadoAlvara alvara;

	private CredencialEndAdic endAdic;
	private CredencialEndAdicHist endAdicHist;

	private Credenciado funcionario;
	private CredencialRelacaoCred relacaoFuncionario;
	private CredencialRelacaoCredHist relacaoFuncionarioHist;

	private PessoaFisica proprietario;
	private CredencialRelacaoProp relacaoProprietario;
	private CredencialRelacaoPropHist relacaoProprietarioHist;

	private CredencialVeiculo veiculo;
	private CredencialRelacaoVeic relacaoVeic;
	private CredencialRelacaoVeicHist relacaoVeicHist;

	private Usuario usuarioLogado;

	private List<CredenciadoEmp> credenciados;
	private List<CredenciadoEmp> credenciadosValidadeDesc;
	private List<PessoaJuridica> empresas;
	private List<CredencialStatus> credencialStatus;
	private List<CredencialEmpTipo> CredencialEmpTipos;

	private List<Cidade> Cidades;
	private Estado estado;
	private List<Estado> Estados;

	private Boolean exibePainelDados;

	private boolean skip;

	private Date dataHoje;
	private Date data10Dias;

	private Usuario usuario;

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public CredenciadoEmp getCredenciadoDaBusca() {
		return credenciadoDaBusca;
	}

	public void setCredenciadoDaBusca(CredenciadoEmp credenciadoDaBusca) {
		this.credenciadoDaBusca = credenciadoDaBusca;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public CredenciadoEmp getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(CredenciadoEmp credenciado) {
		this.credenciado = credenciado;
	}

	public List<CredenciadoEmp> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<CredenciadoEmp> credenciados) {
		this.credenciados = credenciados;
	}

	public List<PessoaJuridica> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<PessoaJuridica> empresas) {
		this.empresas = empresas;
	}

	public List<CredencialStatus> getCredencialStatus() {
		return credencialStatus;
	}

	public void setCredencialStatus(List<CredencialStatus> credencialStatus) {
		this.credencialStatus = credencialStatus;
	}

	public List<CredencialEmpTipo> getCredencialEmpTipos() {
		return CredencialEmpTipos;
	}

	public void setCredencialEmpTipos(List<CredencialEmpTipo> CredencialEmpTipos) {
		this.CredencialEmpTipos = CredencialEmpTipos;
	}

	public List<Cidade> getCidades() {
		return Cidades;
	}

	public void setCidades(List<Cidade> Cidades) {
		this.Cidades = Cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> estados) {
		Estados = estados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CredenciadoEmpHist getCredenciadoEmpHist() {
		return credenciadoEmpHist;
	}

	public void setCredenciadoEmpHist(CredenciadoEmpHist credenciadoEmpHist) {
		this.credenciadoEmpHist = credenciadoEmpHist;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public CredenciadoSGPE getSGPE() {
		return SGPE;
	}

	public void setSGPE(CredenciadoSGPE sGPE) {
		SGPE = sGPE;
	}

	public CredenciadoEmpObs getObs() {
		return obs;
	}

	public void setObs(CredenciadoEmpObs obs) {
		this.obs = obs;
	}

	public CredenciadoPortaria getPortaria() {
		return portaria;
	}

	public void setPortaria(CredenciadoPortaria portaria) {
		this.portaria = portaria;
	}

	public CredenciadoAlvara getAlvara() {
		return alvara;
	}

	public void setAlvara(CredenciadoAlvara alvara) {
		this.alvara = alvara;
	}

	public CredencialEndAdic getEndAdic() {
		return endAdic;
	}

	public void setEndAdic(CredencialEndAdic endAdic) {
		this.endAdic = endAdic;
	}

	public CredencialEndAdicHist getEndAdicHist() {
		return endAdicHist;
	}

	public void setEndAdicHist(CredencialEndAdicHist endAdicHist) {
		this.endAdicHist = endAdicHist;
	}

	public Credenciado getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Credenciado funcionario) {
		this.funcionario = funcionario;
	}

	public CredencialRelacaoCred getRelacaoFuncionario() {
		return relacaoFuncionario;
	}

	public void setRelacaoFuncionario(CredencialRelacaoCred relacaoFuncionario) {
		this.relacaoFuncionario = relacaoFuncionario;
	}

	public CredencialRelacaoCredHist getRelacaoFuncionarioHist() {
		return relacaoFuncionarioHist;
	}

	public void setRelacaoFuncionarioHist(CredencialRelacaoCredHist relacaoFuncionarioHist) {
		this.relacaoFuncionarioHist = relacaoFuncionarioHist;
	}

	public PessoaFisica getProprietario() {
		return proprietario;
	}

	public void setProprietario(PessoaFisica proprietario) {
		this.proprietario = proprietario;
	}

	public CredencialRelacaoProp getRelacaoProprietario() {
		return relacaoProprietario;
	}

	public void setRelacaoProprietario(CredencialRelacaoProp relacaoProprietario) {
		this.relacaoProprietario = relacaoProprietario;
	}

	public CredencialRelacaoPropHist getRelacaoProprietarioHist() {
		return relacaoProprietarioHist;
	}

	public void setRelacaoProprietarioHist(CredencialRelacaoPropHist relacaoProprietarioHist) {
		this.relacaoProprietarioHist = relacaoProprietarioHist;
	}

	public CredencialVeiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(CredencialVeiculo veiculo) {
		this.veiculo = veiculo;
	}

	public CredencialRelacaoVeic getRelacaoVeic() {
		return relacaoVeic;
	}

	public void setRelacaoVeic(CredencialRelacaoVeic relacaoVeic) {
		this.relacaoVeic = relacaoVeic;
	}

	public CredencialRelacaoVeicHist getRelacaoVeicHist() {
		return relacaoVeicHist;
	}

	public void setRelacaoVeicHist(CredencialRelacaoVeicHist relacaoVeicHist) {
		this.relacaoVeicHist = relacaoVeicHist;
	}

	public Date getDataHoje() {
		return dataHoje;
	}

	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}

	public Date getData10Dias() {
		return data10Dias;
	}

	public void setData10Dias(Date data10Dias) {
		this.data10Dias = data10Dias;
	}

	public List<CredenciadoEmp> getCredenciadosValidadeDesc() {
		return credenciadosValidadeDesc;
	}

	public void setCredenciadosValidadeDesc(List<CredenciadoEmp> credenciadosValidadeDesc) {
		this.credenciadosValidadeDesc = credenciadosValidadeDesc;
	}

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			credenciado = new CredenciadoEmp();

			empresa = new PessoaJuridica();
			pessoa = new PessoaFisica();

			credenciadoEmpHist = new CredenciadoEmpHist();

			CredenciadoEmpDAO credenciadoDAO = new CredenciadoEmpDAO();
			credenciados = credenciadoDAO.listar();

			credenciadosValidadeDesc = credenciadoDAO.listarValidadeDesc();

			CredencialStatusDAO statusDAO = new CredencialStatusDAO();
			credencialStatus = statusDAO.listar("tipoStatus");

			CredencialEmpTipoDAO tipoDAO = new CredencialEmpTipoDAO();
			CredencialEmpTipos = tipoDAO.listar("tipocredencial");

			SGPE = new CredenciadoSGPE();
			obs = new CredenciadoEmpObs();
			portaria = new CredenciadoPortaria();
			alvara = new CredenciadoAlvara();

			endAdic = new CredencialEndAdic();
			endAdicHist = new CredencialEndAdicHist();

			funcionario = new Credenciado();
			relacaoFuncionario = new CredencialRelacaoCred();
			relacaoFuncionarioHist = new CredencialRelacaoCredHist();

			proprietario = new PessoaFisica();
			relacaoProprietario = new CredencialRelacaoProp();
			relacaoProprietarioHist = new CredencialRelacaoPropHist();

			veiculo = new CredencialVeiculo();
			relacaoVeic = new CredencialRelacaoVeic();
			relacaoVeicHist = new CredencialRelacaoVeicHist();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("sigla");

			Cidades = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Credenciado.");
			erro.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String convertTime(Date time) {
		dataHoje = new Date();

		dataHoje.setDate(dataHoje.getDate() - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(dataHoje);
	}

	@SuppressWarnings("deprecation")
	public String convert10Dias(Date time) {
		data10Dias = new Date();
		data10Dias.setDate(data10Dias.getDate() + 10);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(data10Dias);
	}

	/*
	 * public void novo() { try { empresa = new PessoaJuridica();
	 * 
	 * EstadoDAO estadoDAO = new EstadoDAO(); Estados = estadoDAO.listar("sigla");
	 * 
	 * Cidades = new ArrayList<>();
	 * 
	 * } catch (RuntimeException erro) { Messages.addGlobalError(
	 * "Ocorreu um erro ao tentar buscar as Pessoas."); erro.printStackTrace(); } }
	 */

	public void salvar() {
		try {

			PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
			pessoaJuridicaDAO.merge(empresa);

			empresa = PessoaJuridicaDAO.carregarCnpj(empresa.getCnpj());

			CredenciadoEmpDAO credenciadoDAO = new CredenciadoEmpDAO();

			credenciado.setPessoaJuridica(empresa);
			credenciado.setDataCadastro(new Date());

			credenciadoDAO.merge(credenciado);

			credenciado = CredenciadoEmpDAO.consultaporCnpj(empresa);

			credenciadoDAO.salvarCredenciado(credenciado, empresa, credenciadoEmpHist, usuarioLogado);

			CredenciadoEmpBean.this.listar();

			Messages.addGlobalInfo("Credenciado cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar o Credenciado.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			credenciado = (CredenciadoEmp) evento.getComponent().getAttributes().get("credenciadoSelecionado");

			CredenciadoEmpDAO credenciadoDAO = new CredenciadoEmpDAO();
			credenciadoDAO.excluir(credenciado);

			CredenciadoEmpBean.this.buscar();

			Messages.addGlobalInfo("Credenciado removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Credenciado.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			credenciado = (CredenciadoEmp) evento.getComponent().getAttributes().get("credenciadoSelecionado");

			PessoaJuridicaDAO empresaDAO = new PessoaJuridicaDAO();
			empresas = empresaDAO.listar();

			CredencialStatusDAO statusDAO = new CredencialStatusDAO();
			credencialStatus = statusDAO.listar("tipoStatus");

			CredencialEmpTipoDAO tipoDAO = new CredencialEmpTipoDAO();
			CredencialEmpTipos = tipoDAO.listar("tipocredencial");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Usuário.");
			erro.printStackTrace();
		}
	}

	public void buscar() {

		CidadeDAO municipioDAO = new CidadeDAO();
		exibePainelDados = true;

		try {

			empresa = PessoaJuridicaDAO.carregarCnpj(empresa.getCnpj());
			credenciado = CredenciadoEmpDAO.consultaporCnpj(empresa);
			if (empresa.getEstadoEndereco() != null) {
				Cidades = municipioDAO.buscarPorEstado(empresa.getEstadoEndereco().getCodigo());
			}
		} catch (NullPointerException e) {
			Messages.addGlobalWarn("Empresa não Credenciada");
			if (empresa == null) {
				estado = new Estado();
				EstadoDAO estadoDAO = new EstadoDAO();
				Estados = estadoDAO.listar("sigla");
				Cidades = new ArrayList<>();
			} else if (empresa.getEstadoEndereco() != null) {
				Cidades = municipioDAO.buscarPorEstado(empresa.getEstadoEndereco().getCodigo());
			}
		}
	}

	public void buscarCredenciado() {
		try {
			empresa = PessoaJuridicaDAO.carregarCnpj(empresa.getCnpj());

			credenciadoDaBusca = CredenciadoEmpDAO.consultaporCnpj(empresa);

			if (empresa == null) {
				CredenciadoEmpBean.this.listar();
				exibePainelDados = false;

			} else if (empresa != null) {
				credenciado = CredenciadoEmpDAO.consultaporCnpj(empresa);

				if (credenciado == null) {
					CredenciadoEmpBean.this.listar();
					exibePainelDados = false;

				} else if (credenciado != null) {
					CidadeDAO municipioDAO = new CidadeDAO();
					Cidades = municipioDAO.buscarPorEstado(empresa.getEstadoEndereco().getCodigo());
					exibePainelDados = true;
				}
			}
		} catch (NullPointerException e) {
			Messages.addGlobalError("Empresa não credenciada.");

			CredenciadoEmpBean.this.listar();
		}
	}

	public void buscarProprietario() {
		try {
			proprietario = PessoaDAO.carregarCpf(pessoa.getCpf());

		} catch (NullPointerException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o pessoa");
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaProprietario() {
		try {

			CredenciadoEmpBean.this.buscarProprietario();

			relacaoProprietario.setPessoa(proprietario);
			relacaoProprietario.setCredenciado(credenciadoDaBusca);
			relacaoProprietario.setDataInclusao(new Date());
			relacaoProprietario.setUsuarioInclusao(usuarioLogado);

			CredencialRelacaoPropDAO proprietarioDAO = new CredencialRelacaoPropDAO();
			proprietarioDAO.merge(relacaoProprietario);

			relacaoProprietarioHist.setAcao(1);
			relacaoProprietarioHist.setPessoa(proprietario);
			relacaoProprietarioHist.setCredenciado(credenciadoDaBusca);

			CredencialRelacaoPropHistDAO proprietarioHistDAO = new CredencialRelacaoPropHistDAO();
			proprietarioHistDAO.atualizaHistorico(usuarioLogado, relacaoProprietarioHist);

			Messages.addGlobalInfo("Proprietário incluído com sucesso!");

			relacaoProprietario = new CredencialRelacaoProp();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o Proprietário.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void excluirProprietario(ActionEvent evento) {

		try {
			relacaoProprietario = (CredencialRelacaoProp) evento.getComponent().getAttributes()
					.get("ProprietarioSelecionado");

			relacaoProprietarioHist.setAcao(2);
			relacaoProprietarioHist.setPessoa(relacaoProprietario.getPessoa());
			relacaoProprietarioHist.setCredenciado(relacaoProprietario.getCredenciado());

			CredencialRelacaoPropDAO proprietarioDAO = new CredencialRelacaoPropDAO();
			proprietarioDAO.excluir(relacaoProprietario);

			CredencialRelacaoPropHistDAO proprietarioHistDAO = new CredencialRelacaoPropHistDAO();
			proprietarioHistDAO.atualizaHistorico(usuarioLogado, relacaoProprietarioHist);

			relacaoProprietario = new CredencialRelacaoProp();

			CredenciadoEmpBean.this.buscar();

			Messages.addGlobalInfo("Proprietário removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Proprietário.");
			erro.printStackTrace();
		}
	}

	public void buscarFuncionario() {
		try {
			pessoa = PessoaDAO.carregarCpf(pessoa.getCpf());

			funcionario = CredenciadoDAO.consultaporPessoa(pessoa);

			if (pessoa == null) {
				CredenciadoEmpBean.this.buscar();
				exibePainelDados = false;

			} else if (pessoa != null) {
				funcionario = CredenciadoDAO.consultaporPessoa(pessoa);

				if (credenciado == null) {
					CredenciadoEmpBean.this.buscar();
					exibePainelDados = false;

				} else if (credenciado != null) {
					CidadeDAO municipioDAO = new CidadeDAO();
					Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
					exibePainelDados = true;
				}
			}
		} catch (NullPointerException e) {
			Messages.addGlobalError("Pessoa não credenciada ou Inativa/Bloqueada.");

			CredenciadoEmpBean.this.buscar();
		}

	}

	public void adicionaFuncionario() {
		try {

			CredenciadoEmpBean.this.buscarFuncionario();

			relacaoFuncionario.setCredenciado(funcionario);
			relacaoFuncionario.setCredenciadoEmp(credenciadoDaBusca);
			relacaoFuncionario.setDataInclusao(new Date());
			relacaoFuncionario.setUsuarioInclusao(usuarioLogado);

			CredencialRelacaoCredDAO funcionarioDAO = new CredencialRelacaoCredDAO();
			funcionarioDAO.merge(relacaoFuncionario);

			relacaoFuncionarioHist.setAcao(1);
			relacaoFuncionarioHist.setCredenciado(funcionario);
			relacaoFuncionarioHist.setCredenciadoEmp(credenciadoDaBusca);

			CredencialRelacaoCredHistDAO funcionarioHistDAO = new CredencialRelacaoCredHistDAO();
			funcionarioHistDAO.atualizaHistorico(usuarioLogado, relacaoFuncionarioHist);

			Messages.addGlobalInfo("Funcionário incluído com sucesso!");

			relacaoFuncionario = new CredencialRelacaoCred();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir Funcionário.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void excluirFuncionario(ActionEvent evento) {

		try {
			relacaoFuncionario = (CredencialRelacaoCred) evento.getComponent().getAttributes()
					.get("funcionarioSelecionado");

			relacaoFuncionarioHist.setAcao(2);
			relacaoFuncionarioHist.setCredenciado(relacaoFuncionario.getCredenciado());
			relacaoFuncionarioHist.setCredenciadoEmp(relacaoFuncionario.getCredenciadoEmp());

			CredencialRelacaoCredDAO funcionarioDAO = new CredencialRelacaoCredDAO();
			funcionarioDAO.excluir(relacaoFuncionario);

			CredencialRelacaoCredHistDAO funcionarioHistDAO = new CredencialRelacaoCredHistDAO();
			funcionarioHistDAO.atualizaHistorico(usuarioLogado, relacaoFuncionarioHist);

			relacaoFuncionario = new CredencialRelacaoCred();

			CredenciadoEmpBean.this.buscar();

			Messages.addGlobalInfo("Funcionário removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Funcionário.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaEnd() {
		try {
			endAdic.setUsuarioInclusao(usuarioLogado);
			endAdic.setDataInclusao(new Date());
			endAdic.setCredenciadoEmp(credenciadoDaBusca);

			CredencialEndAdicDAO endAdicDAO = new CredencialEndAdicDAO();
			endAdicDAO.merge(endAdic);

			endAdicHist.setEndereco(endAdic.getEndereco());
			endAdicHist.setTipoendereco(endAdic.getTipoendereco());
			endAdicHist.setAcao(1);
			endAdicHist.setCredenciadoEmp(credenciadoDaBusca);

			CredencialEndAdicHistDAO endAdicHistDAO = new CredencialEndAdicHistDAO();
			endAdicHistDAO.atualizaHistorico(usuarioLogado, endAdicHist);

			Messages.addGlobalInfo("Endereço Adicional incluído com sucesso!");

			endAdic = new CredencialEndAdic();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir Endereço Adicional.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void excluirEnd(ActionEvent evento) {

		try {
			endAdic = (CredencialEndAdic) evento.getComponent().getAttributes().get("enderecoSelecionado");

			endAdicHist.setEndereco(endAdic.getEndereco());
			endAdicHist.setTipoendereco(endAdic.getTipoendereco());
			endAdicHist.setAcao(2);
			endAdicHist.setCredenciadoEmp(credenciadoDaBusca);

			CredencialEndAdicDAO endAdicDAO = new CredencialEndAdicDAO();
			endAdicDAO.excluir(endAdic);

			CredencialEndAdicHistDAO endAdicHistDAO = new CredencialEndAdicHistDAO();
			endAdicHistDAO.atualizaHistorico(usuarioLogado, endAdicHist);

			endAdic = new CredencialEndAdic();

			CredenciadoEmpBean.this.buscar();

			Messages.addGlobalInfo("Endereço removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Endereço.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaSGPE() {
		try {

			SGPE.setUsuarioCadastro(usuarioLogado);
			SGPE.setDataInclusao(new Date());
			SGPE.setEmpresa(credenciadoDaBusca);

			CredenciadoSGPEDAO sgpeDAO = new CredenciadoSGPEDAO();
			sgpeDAO.merge(SGPE);

			Messages.addGlobalInfo("SGP-e incluído com sucesso!");

			SGPE = new CredenciadoSGPE();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o SGP-e.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaObs() {
		try {

			obs.setUsuarioCadastro(usuarioLogado);
			obs.setDataInclusao(new Date());
			obs.setEmpresa(credenciadoDaBusca);

			CredenciadoEmpObsDAO obsDAO = new CredenciadoEmpObsDAO();
			obsDAO.merge(obs);

			Messages.addGlobalInfo("Observação incluído com sucesso!");

			obs = new CredenciadoEmpObs();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir a Observação.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaAlvara() {
		try {

			alvara.setUsuarioCadastro(usuarioLogado);
			alvara.setDataInclusao(new Date());
			alvara.setEmpresa(credenciadoDaBusca);

			CredenciadoAlvaraDAO alvaraDAO = new CredenciadoAlvaraDAO();
			alvaraDAO.merge(alvara);

			Messages.addGlobalInfo("Alvará incluído com sucesso!");

			alvara = new CredenciadoAlvara();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o Alvará.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaPortaria() {
		try {

			portaria.setUsuarioCadastro(usuarioLogado);
			portaria.setDataInclusao(new Date());
			portaria.setEmpresa(credenciadoDaBusca);

			CredenciadoPortariaDAO portariaDAO = new CredenciadoPortariaDAO();
			portariaDAO.merge(portaria);

			Messages.addGlobalInfo("Portaria incluída com sucesso!");

			portaria = new CredenciadoPortaria();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir a Portaria.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void popular() {
		try {
			if (empresa.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				Cidades = municipioDAO.buscarPorEstado(empresa.getEstadoEndereco().getCodigo());
			} else {
				Cidades = new ArrayList<>();
			}
		} catch (NullPointerException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as Cidades");
		}
	}

	public void buscarVeiculo() {
		try {
			CredencialVeiculoDAO veiculoDAO = new CredencialVeiculoDAO();

			veiculo = veiculoDAO.carregarVeiculo(veiculo.getPlaca());

		} catch (NullPointerException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o Veículo");
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void adicionaVeiculo() {
		try {

			CredenciadoEmpBean.this.buscarVeiculo();

			relacaoVeic.setVeiculo(veiculo);
			relacaoVeic.setEmpresa(credenciadoDaBusca);
			relacaoVeic.setDataInclusao(new Date());
			relacaoVeic.setUsuarioInclusao(usuarioLogado);

			CredencialRelacaoVeicDAO veiculoDAO = new CredencialRelacaoVeicDAO();
			veiculoDAO.merge(relacaoVeic);

			relacaoVeicHist.setAcao(1);
			relacaoVeicHist.setVeiculo(veiculo);
			relacaoVeicHist.setEmpresa(credenciadoDaBusca);

			CredencialRelacaoVeicHistDAO veiculoHistDAO = new CredencialRelacaoVeicHistDAO();
			veiculoHistDAO.atualizaHistorico(usuarioLogado, relacaoVeicHist);

			Messages.addGlobalInfo("Veículo incluído com sucesso!");

			relacaoVeic = new CredencialRelacaoVeic();

			CredenciadoEmpBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o Proprietário.");
			erro.printStackTrace();
			CredenciadoEmpBean.this.buscar();
		}
	}

	public void excluirVeiculo(ActionEvent evento) {

		try {
			relacaoVeic = (CredencialRelacaoVeic) evento.getComponent().getAttributes().get("VeiculoSelecionado");

			relacaoVeicHist.setAcao(2);
			relacaoVeicHist.setVeiculo(relacaoVeic.getVeiculo());
			relacaoVeicHist.setEmpresa(relacaoVeic.getEmpresa());

			CredencialRelacaoVeicDAO veiculoDAO = new CredencialRelacaoVeicDAO();
			veiculoDAO.excluir(relacaoVeic);

			CredencialRelacaoVeicHistDAO veicHistDAO = new CredencialRelacaoVeicHistDAO();
			veicHistDAO.atualizaHistorico(usuarioLogado, relacaoVeicHist);

			relacaoVeic = new CredencialRelacaoVeic();

			CredenciadoEmpBean.this.buscar();

			Messages.addGlobalInfo("Veículo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Veículo.");
			erro.printStackTrace();
		}
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirma";
		} else {
			return event.getNewStep();
		}
	}
	


}

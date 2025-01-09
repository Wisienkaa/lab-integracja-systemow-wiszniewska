class RisksController < ApplicationController
  helper :risks
  before_action :find_project, :authorize, only: [:index, :show, :edit, :update, :create]
  before_action :find_risk, only: [:show, :edit, :update]
  
  # Autoryzacja dostępu do widoku ryzyk w projekcie
  def authorize
    unless User.current.allowed_to?(:view_issues, @project)
      render_403
    end
  end

  # Wyświetlanie listy ryzyk dla projektu
  def index
    @risks = @project.risks
  end

  # Wyświetlanie szczegółów pojedynczego ryzyka
  def show
    @spike_task = SpikeTask.new # Formularz do dodawania spike tasków
  end

  # Formularz do tworzenia nowego ryzyka
  def new
    @project = Project.find(params[:project_id])  # Znajdujemy projekt na podstawie id w URL
    @risk = @project.risks.build  # Tworzymy nowy obiekt ryzyka dla tego projektu
  end
  

  def all_risks
    @risks = Risk.all  # Pobierz wszystkie ryzyka z bazy danych
    @projects = Project.all
  end

  
  def destroy
    @risk.destroy
    redirect_to project_risks_path(@project), notice: 'Ryzyko zostało usunięte.'  # Przekierowanie z komunikatem o sukcesi
  end
  
  

  
  # Tworzenie nowego ryzyka
  def create
    @risk = Risk.new(risk_params)
    @risk.project = @project
    if @risk.save
      redirect_to project_risks_path(@project), notice: 'Risk successfully created.'
    else
      render :new
    end
  end

  # Formularz do edytowania istniejącego ryzyka
  def edit
    # W tym przypadku formularz jest już wstępnie wypełniony
  end

  # Aktualizacja istniejącego ryzyka
  def update
    if @risk.update(risk_params)
      redirect_to project_risk_path(@project, @risk), notice: 'Risk successfully updated.'
    else
      render :edit
    end
  end

  private

  # Znajdowanie projektu
  def find_project
    @project = Project.find_by(identifier: params[:project_id])
    if @project.nil?
      flash[:error] = "Nie znaleziono projektu"
      redirect_to root_path # Możesz przekierować użytkownika na stronę główną lub inną stronę
    end
  end

  # Znajdowanie ryzyka na podstawie ID
  def find_risk
    if @project
      @risk = @project.risks.find_by(id: params[:id]) # Używamy find_by, aby uniknąć wyjątku, jeśli nie znajdziemy ryzyka
      if @risk.nil?
        flash[:error] = "Nie znaleziono ryzyka"
        redirect_to project_risks_path(@project) # Przekierowanie na stronę listy ryzyk, jeśli ryzyko nie istnieje
      end
    end
  end

  # Parametry dopuszczalne dla ryzyka
  def risk_params
    params.require(:risk).permit(:title, :description, :likelihood, :impact, :assignee_id, :category, :status, :response)
  end



end

class RiskManagement::ProjectsController < ApplicationController
  before_action :set_project
  
  def show
    # Pobierz ryzyka związane z projektem
    @risks = @project.risks
  end

  def edit
    # Formularz edycji projektu
  end

  def update
    if @project.update(project_params)
      flash[:notice] = 'Projekt został zaktualizowany.'
      redirect_to project_path(@project)
    else
      flash[:error] = 'Nie udało się zaktualizować projektu.'
      render :edit
    end
  end

  private

  def set_project
    @project = Project.find(params[:id])
  end

  def project_params
    params.require(:project).permit(:name, :description, :start_date, :due_date, :estimated_time, :priority, :author_id, :assignee_id)
  end
  
  end
end

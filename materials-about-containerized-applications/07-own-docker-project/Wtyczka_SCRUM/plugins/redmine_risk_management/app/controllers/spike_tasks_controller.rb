class SpikeTasksController < ApplicationController
  before_action :find_risk
  before_action :find_spike_task, only: [:show, :update]

  def create
    @spike_task = @risk.spike_tasks.new(spike_task_params)
    if @spike_task.save
      redirect_to project_risk_path(@risk.project, @risk), notice: "Spike task added successfully."
    else
      redirect_to project_risk_path(@risk.project, @risk), alert: "Failed to add spike task."
    end
  end

  def show
    # Wyświetla szczegóły spike taska
  end

  def update
    if @spike_task.update(spike_task_params)
      redirect_to project_risk_spike_task_path(@risk.project, @risk, @spike_task), notice: "Spike task updated successfully."
    else
      render :show, alert: "Failed to update spike task."
    end
  end

  private

  def find_risk
    @risk = Risk.find(params[:risk_id])
  end

  def find_spike_task
    @spike_task = @risk.spike_tasks.find(params[:id])
  end

  def spike_task_params
    params.require(:spike_task).permit(:name, :description, :status)
  end
end

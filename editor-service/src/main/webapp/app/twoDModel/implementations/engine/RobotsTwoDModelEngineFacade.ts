/// <reference path="../../interfaces/two-d-model-core.d.ts" />
/// <reference path="../../interfaces/vendor.d.ts" />

class RobotsTwoDModelEngineFacade extends TwoDModelEngineFacadeImpl {

    constructor($scope, $compile, $attrs) {
        super($scope, $compile, $attrs);

        var facade = this;
        $(document).ready(() => {
            $('#two-d-model-clear-confirmation-window').find('.modal-footer #confirm').on('click', function() {
                facade.model.getWorldModel().clearScene();
                $('#two-d-model-clear-confirmation-window').modal('hide');
            });
        });

        $scope.start = () => {
            var timeline = this.model.getTimeline();
            $scope.$emit("emitInterpret", timeline);
        }
        $scope.resetPosition = () => { this.resetPosition(); };
        
        $scope.stop = () => {
            $scope.$emit("emitStop");
        }

        $scope.openDiagramEditor = () => { this.openDiagramEditor(); };

        // wall-button is disabled while there is no collision detection
        $("#wall-button").prop('disabled', true);
    }

    public openDiagramEditor(): void {
        $("#two-d-model-area").hide();
        $("#diagram-area").show();
    }

    public resetPosition(): void {
        var robotModel = this.model.getRobotModels()[0];
        robotModel.clearCurrentPosition();
    }

}
